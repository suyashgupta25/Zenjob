package com.zenjob.ui.login.loginscreen

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import com.zenjob.data.model.LoginRequest
import com.zenjob.data.remote.ZenjobService
import com.zenjob.utils.defaultErrorHandler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val webService: ZenjobService) : ViewModel() {

    // Disposable
    private val disposable: CompositeDisposable = CompositeDisposable()

    val loginReq = ObservableField<LoginRequest>()

    private fun postLogin() {
        disposable.add(webService.postLogin(loginReq.get()!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(defaultErrorHandler())
                .subscribe({
                    System.out.println("token:" + it.accessToken)
                }, {
                    System.err.println(it)
                }, {
                })
        )
    }

    fun setEmail() {
    }


    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}