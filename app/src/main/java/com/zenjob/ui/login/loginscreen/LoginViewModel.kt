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
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val webService: ZenjobService, private val prefsHelper: PrefsHelper) : ViewModel() {

    // Disposable
    private val disposable: CompositeDisposable = CompositeDisposable()

    val email = ObservableField<String>()
    val password = ObservableField<String>()
    val networkState = MutableLiveData<NetworkState>()

    fun checkForAppSession() = prefsHelper.checkForSessionAvailable()

    fun login() {
        networkState.value = NetworkState.LOADING
        disposable.add(webService.postLogin(LoginRequest(email.get(), password.get()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(defaultErrorHandler())
                .subscribe({
                    prefsHelper.saveUserAuth(it)
                    networkState.value = NetworkState.LOADED
                }, {
                    System.err.println(it)
                    networkState.value = NetworkState(Status.FAILED, it.localizedMessage)
                }, {
                })
        )
    }

    fun dummy() {
        email.set("suyash.gupta25@gmail.com")
        password.set("testing12345")
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}