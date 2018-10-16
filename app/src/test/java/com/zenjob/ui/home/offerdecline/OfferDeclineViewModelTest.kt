package com.zenjob.ui.home.offerdecline

import android.app.Application
import android.arch.lifecycle.Observer
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.mock
import com.zenjob.BuildConfig
import com.zenjob.data.model.*
import com.zenjob.data.remote.ZenjobService
import com.zenjob.utils.rx.TestSchedulerProvider
import io.reactivex.Observable
import io.reactivex.schedulers.TestScheduler
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.concurrent.TimeUnit

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, sdk = [21], application = Application::class)
class OfferDeclineViewModelTest {

    val service: ZenjobService = mock()

    var tsp: TestSchedulerProvider? = null
    var ts: TestScheduler? = null
    var viewModel: OfferDeclineViewModel? = null

    var observer: Observer<List<ReasonsItem>> = mock()
    var observer2: Observer<Result<OfferDeclineResponse>> = mock()

    @Before
    fun createDependencies() {
        ts = TestScheduler()
        tsp = TestSchedulerProvider(ts!!)
        viewModel = OfferDeclineViewModel(service, tsp!!)
    }

    @Test
    fun testGetOfferDeclineReasons() {
        val response = OfferDeclineReasonsResponse(listOf<ReasonsItem>())

        given(service.getOfferReasons()).willReturn(Observable.just(response))

        viewModel!!.reasons.observeForever(observer)
        viewModel!!.getOfferDeclineReasons()

        //PROVIDES THE ADVANCED TIME BEHAVIOUR
        ts!!.advanceTimeBy(500, TimeUnit.MILLISECONDS)

        Mockito.verify(observer, Mockito.times(1)).onChanged(any())
        Assert.assertEquals(viewModel!!.networkViewModel.showError.get(), false)
        Assert.assertEquals(viewModel!!.networkViewModel.showProgress.get(), false)
    }

    @Test
    fun testPrepareAndExecuteRequest() {
        val response = OfferDeclineResponse()


        val listOf = mutableListOf<ReasonsItem>()
        listOf.add(createTestData())
        listOf.add(createTestData())
        viewModel!!.reasons.postValue(listOf)

        val req = OfferDeclineRequest()
        req.reason = "OTHER"
        given(service.declineOffer("id", req)).willReturn(Observable.just(response))

        viewModel!!.offerDeclineResponse.observeForever(observer2)
        viewModel!!.prepareAndExecuteRequest("id", 1)

        //PROVIDES THE ADVANCED TIME BEHAVIOUR
        ts!!.advanceTimeBy(500, TimeUnit.MILLISECONDS)

        Mockito.verify(observer2, Mockito.times(2)).onChanged(any())
        Assert.assertEquals(viewModel!!.networkViewModel.showError.get(), false)
        Assert.assertEquals(viewModel!!.networkViewModel.showProgress.get(), false)
    }

    @After
    fun releaseDependencies() {
        ts = null
        tsp = null
        viewModel = null
    }

    fun createTestData(): ReasonsItem {
        return ReasonsItem(null, "OTHER", false, "", "")

    }
}