package com.zenjob.ui.home.offers

import android.app.Application
import android.arch.lifecycle.Observer
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.mock
import com.zenjob.BuildConfig
import com.zenjob.data.model.OffersItem
import com.zenjob.data.model.OffersListResponse
import com.zenjob.data.model.Result
import com.zenjob.data.remote.ZenjobService
import com.zenjob.utils.rx.TestSchedulerProvider
import io.reactivex.Observable
import io.reactivex.schedulers.TestScheduler
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.concurrent.TimeUnit

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, sdk = [21], application = Application::class)

class OffersViewModelTest {

    val service: ZenjobService = mock()

    var tsp: TestSchedulerProvider? = null
    var ts: TestScheduler? = null
    var viewModel: OffersViewModel? = null

    var observer: Observer<Result<List<OffersItem>>> = mock()

    @Before
    fun createDependencies() {
        ts = TestScheduler()
        tsp = TestSchedulerProvider(ts!!)
        viewModel = OffersViewModel(service, tsp!!)

    }

    @Test
    fun testOffers() {
        given(service.getOffers()).willReturn(Observable.just(OffersListResponse()))

        viewModel!!.offerList.observeForever(observer)
        viewModel!!.getOffers()

        //PROVIDES THE ADVANCED TIME BEHAVIOUR
        ts!!.advanceTimeBy(500, TimeUnit.MILLISECONDS)

        Mockito.verify(observer, Mockito.times(2)).onChanged(any())
    }

    @After
    fun releaseDependencies() {
        ts = null
        tsp = null
        viewModel = null
    }
}