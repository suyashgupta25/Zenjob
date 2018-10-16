package com.zenjob.ui.home.offerdecline

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import com.zenjob.data.model.OfferDeclineRequest
import com.zenjob.data.model.OfferDeclineResponse
import com.zenjob.data.model.ReasonsItem
import com.zenjob.data.model.Result
import com.zenjob.data.remote.NetworkState
import com.zenjob.data.remote.Status
import com.zenjob.data.remote.ZenjobService
import com.zenjob.ui.common.viewmodels.ItemNetworkStateViewModel
import com.zenjob.utils.defaultErrorHandler
import com.zenjob.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class OfferDeclineViewModel @Inject constructor(private val webService: ZenjobService,
                                                private val schedulerProvider: SchedulerProvider) : ViewModel() {

    // Disposable
    private val disposable: CompositeDisposable = CompositeDisposable()

    val reasons = MutableLiveData<List<ReasonsItem>>()
    val reasonStep2 = ObservableField<String>()
    val networkViewModel = ItemNetworkStateViewModel(NetworkState(Status.RUNNING, NetworkState.MSG_RUNNING))

    val offerDeclineResponse = MutableLiveData<Result<OfferDeclineResponse>>()

    fun getOfferDeclineReasons() {
        networkViewModel.showProgress.set(true)
        networkViewModel.showError.set(false)
        disposable.add(webService.getOfferReasons()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .doOnError(defaultErrorHandler())
                .subscribe({
                    networkViewModel.showProgress.set(false)
                    networkViewModel.showError.set(false)
                    reasons.value = it.reasons
                }, {
                    networkViewModel.showProgress.set(false)
                    networkViewModel.showError.set(true)
                    networkViewModel.errorMsg.set(it.localizedMessage)
                    reasons.value = listOf()
                }, {
                })
        )
    }

    fun isStep2RadioButton(checkedRadioButtonId: Int): Boolean {
        reasons.value?.let {
            val reasonsItem = it[checkedRadioButtonId]
            if (reasonsItem.name!!.equals("JOB_NOT_SUITABLE_CATEGORY") ||
                    reasonsItem.needsComment!!) {
                return true
            }
        }
        return false
    }

    fun prepareAndExecuteRequest(offerId: String, checkedRadioButtonId: Int?) {
        reasons.value?.let {
            checkedRadioButtonId?.let { t: Int ->
                val reasonsItem = it[t]
                val temp = OfferDeclineRequest()
                temp.reason = reasonsItem.name
                if (reasonsItem.name.equals("JOB_NOT_SUITABLE_CATEGORY") ||
                        reasonsItem.needsComment!!) {
                    temp.reasonComment = reasonStep2.get()
                }
                offerDecline(offerId, temp)
            }
        }
    }

    private fun offerDecline(offerId: String, offerDecline: OfferDeclineRequest) {
        networkViewModel.showProgress.set(true)
        networkViewModel.showError.set(false)
        disposable.add(webService.declineOffer(offerId, offerDecline)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .doOnError(defaultErrorHandler())
                .map { Result.success(it) }
                .startWith(Result.loading())
//                .delaySubscription(500, TimeUnit.MILLISECONDS)
                .onErrorReturn { Result.failure(it) }
                .subscribe({
                    networkViewModel.showProgress.set(false)
                    networkViewModel.showError.set(false)
                    offerDeclineResponse.postValue(it)
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