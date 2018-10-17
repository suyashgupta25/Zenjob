package com.zenjob.ui.login.loginscreen

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import com.zenjob.data.local.PrefsHelper
import com.zenjob.data.model.LoginRequest
import com.zenjob.data.remote.NetworkState
import com.zenjob.data.remote.Status
import com.zenjob.data.remote.ZenjobService
import com.zenjob.utils.defaultErrorHandler
import com.zenjob.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val webService: ZenjobService,
                                         private val prefsHelper: PrefsHelper,
                                         private val schedulerProvider: SchedulerProvider) : ViewModel() {

    // Disposable
    private val disposable: CompositeDisposable = CompositeDisposable()

    val email = ObservableField<String>()
    val password = ObservableField<String>()
    val networkState = MutableLiveData<NetworkState>()

    fun checkForAppSession() = prefsHelper.checkForSessionAvailable()

    fun login() {
        networkState.postValue(NetworkState.LOADING)
        disposable.add(
                webService.postLogin(LoginRequest(email.get(), password.get()))
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .doOnError(defaultErrorHandler())
                        .subscribe({
                            prefsHelper.saveUserAuth(it)
                            networkState.postValue(NetworkState.LOADED)
                        }, {
                            System.err.println(it)
                            networkState.postValue(NetworkState(Status.FAILED, it.localizedMessage))
                        }, {
                        })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}