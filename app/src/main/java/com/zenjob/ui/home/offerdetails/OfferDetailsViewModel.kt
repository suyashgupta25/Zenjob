package com.zenjob.ui.home.offerdetails

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import com.zenjob.data.model.OffersItem
import com.zenjob.data.model.Result
import com.zenjob.data.remote.NetworkState
import com.zenjob.data.remote.Status
import com.zenjob.data.remote.ZenjobService
import com.zenjob.ui.common.viewmodels.ItemNetworkStateViewModel
import com.zenjob.utils.defaultErrorHandler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class OfferDetailsViewModel @Inject constructor(private val webService: ZenjobService) : ViewModel() {

    // Disposable
    private val disposable: CompositeDisposable = CompositeDisposable()

    val offerResponse = MutableLiveData<Result<OffersItem>>()
    val offer = ObservableField<OffersItem>()
    val networkViewModel = ItemNetworkStateViewModel(NetworkState(Status.RUNNING, NetworkState.MSG_RUNNING))

    fun getOfferDetails(id: String?) {
        disposable.add(webService.getOfferDetails(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { Result.success(it) }
                .doOnError(defaultErrorHandler())
                .onErrorReturn { Result.failure(it) }
                .startWith(Result.loading())
                .subscribe({
                    offerResponse.postValue(it)
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}