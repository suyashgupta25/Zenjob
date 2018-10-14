package com.zenjob.ui.home.offers

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.zenjob.data.model.OffersItem
import com.zenjob.data.model.Result
import com.zenjob.data.remote.ZenjobService
import com.zenjob.utils.defaultErrorHandler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class OffersViewModel @Inject constructor(private val webService: ZenjobService) : ViewModel() {

    // Disposable
    private val disposable: CompositeDisposable = CompositeDisposable()

    val offerList = MutableLiveData<Result<List<OffersItem>>>()

    fun getOffers() {
        disposable.add(webService.getOffers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
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