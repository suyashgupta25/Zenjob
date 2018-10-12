package com.zenjob.ui.home.offers

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import com.zenjob.data.remote.NetworkState
import com.zenjob.data.remote.Status
import com.zenjob.data.remote.ZenjobService
import com.zenjob.ui.common.listeners.ErrorRetryListener
import com.zenjob.utils.AppConstants.Companion.EMPTY
import com.zenjob.utils.defaultErrorHandler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class OffersViewModel @Inject constructor(private val webService: ZenjobService) : ViewModel(), ErrorRetryListener {

    // Disposable
    private val disposable: CompositeDisposable = CompositeDisposable()
    val networkState = MutableLiveData<NetworkState>()

    val isLoading = ObservableBoolean(true)
    val errorMsg = ObservableField<String>(EMPTY)

    fun getOffers() {
        networkState.value = NetworkState.LOADING
        disposable.add(webService.getOffers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(defaultErrorHandler())
                .subscribe({
                    networkState.value = NetworkState.LOADED
                }, {
                    System.err.println(it)
                    networkState.value = NetworkState(Status.FAILED, it.localizedMessage)
                }, {
                })
        )
    }

    override fun retryClicked() {

    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}