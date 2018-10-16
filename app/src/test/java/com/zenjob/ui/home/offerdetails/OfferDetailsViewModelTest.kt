package com.zenjob.ui.home.offerdetails

import android.app.Application
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.mock
import com.zenjob.BuildConfig
import com.zenjob.data.model.OffersItem
import com.zenjob.data.remote.ZenjobService
import com.zenjob.utils.rx.TestSchedulerProvider
import io.reactivex.Observable
import io.reactivex.schedulers.TestScheduler
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.concurrent.TimeUnit


@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, sdk = [21], application = Application::class)
class OfferDetailsViewModelTest {

    val service: ZenjobService = mock()

    var tsp: TestSchedulerProvider? = null
    var ts: TestScheduler? = null
    var viewModel: OfferDetailsViewModel? = null

    @Before
    fun createDependencies() {
        ts = TestScheduler()
        tsp = TestSchedulerProvider(ts!!)
        viewModel = OfferDetailsViewModel(service, tsp!!)
    }

    @Test
    fun testOfferDetails() {
        val offerItem = OffersItem()
        given(service.getOfferDetails("id")).willReturn(Observable.just(offerItem))

        viewModel!!.getOfferDetails("id")

        //PROVIDES THE ADVANCED TIME BEHAVIOUR
        ts!!.advanceTimeBy(500, TimeUnit.MILLISECONDS)

        Assert.assertEquals(viewModel!!.networkViewModel.showError.get(), false)
        Assert.assertEquals(viewModel!!.networkViewModel.showProgress.get(), false)
    }

    @After
    fun releaseDependencies() {
        ts = null
        tsp = null
        viewModel = null
    }


}