package com.zenjob.ui.home.offerdetails

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import com.zenjob.data.model.OffersItem
import com.zenjob.data.remote.NetworkState
import com.zenjob.data.remote.Status
import com.zenjob.data.remote.ZenjobService
import com.zenjob.ui.common.viewmodels.ItemNetworkStateViewModel
import com.zenjob.utils.defaultErrorHandler
import com.zenjob.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class OfferDetailsViewModel @Inject constructor(private val webService: ZenjobService,
                                                private val schedulerProvider: SchedulerProvider) : ViewModel() {

    // Disposable
    private val disposable: CompositeDisposable = CompositeDisposable()

    val offer = ObservableField<OffersItem>()
    val networkViewModel = ItemNetworkStateViewModel(NetworkState(Status.RUNNING, NetworkState.MSG_RUNNING))

    fun getOfferDetails(id: String?) {
        networkViewModel.showProgress.set(true)
        networkViewModel.showError.set(false)
        disposable.add(webService.getOfferDetails(id)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .doOnError { defaultErrorHandler() }
                .subscribe({
                    networkViewModel.showProgress.set(false)
                    networkViewModel.showError.set(false)
                    offer.set(it)
                }, {
                    networkViewModel.showProgress.set(false)
                    networkViewModel.showError.set(true)
                    networkViewModel.errorMsg.set(it.localizedMessage)
                }, {
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}