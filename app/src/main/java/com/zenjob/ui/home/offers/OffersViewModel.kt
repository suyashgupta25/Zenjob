package com.zenjob.ui.home.offers

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.zenjob.data.model.OffersItem
import com.zenjob.data.model.Result
import com.zenjob.data.remote.ZenjobService
import com.zenjob.utils.defaultErrorHandler
import com.zenjob.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class OffersViewModel @Inject constructor(private val webService: ZenjobService,
                                          private val schedulerProvider: SchedulerProvider) : ViewModel() {

    // Disposable
    private val disposable: CompositeDisposable = CompositeDisposable()
    val offerList = MutableLiveData<Result<List<OffersItem>>>()

    fun getOffers() {
        disposable.add(webService.getOffers()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .map { Result.success(it.offers!!) }
                .doOnError(defaultErrorHandler())
                .onErrorReturn { Result.failure(it) }
                .startWith(Result.loading())
                .subscribe({
                    offerList.postValue(it)
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}