package com.zenjob.ui.login.loginscreen

import android.app.Application
import android.arch.lifecycle.Observer
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.mock
import com.zenjob.BuildConfig
import com.zenjob.data.local.PrefsHelper
import com.zenjob.data.model.LoginRequest
import com.zenjob.data.model.LoginResponse
import com.zenjob.data.remote.NetworkState
import com.zenjob.data.remote.Status
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
class LoginViewModelTest {

    val service: ZenjobService = mock()
    val helper: PrefsHelper = mock()

    var viewModel: LoginViewModel? = null
    var observer: Observer<NetworkState> = mock()
    var tsp: TestSchedulerProvider? = null
    var ts: TestScheduler? = null

    @Before
    fun createDependencies() {
        ts = TestScheduler()
        tsp = TestSchedulerProvider(ts!!)
        viewModel = LoginViewModel(service, helper, tsp!!)

        viewModel!!.email.set("suyash.gupta25@gmail.com")
        viewModel!!.password.set("testing12345")
    }

    @Test
    fun testLogin() {
        val response = LoginResponse()
        given(service.postLogin(LoginRequest(viewModel!!.email.get(), viewModel!!.password.get()))).willReturn(Observable.just(response))

        viewModel!!.networkState.observeForever(observer)
        viewModel!!.login()

        //PROVIDES THE ADVANCED TIME BEHAVIOUR
        ts!!.advanceTimeBy(500, TimeUnit.MILLISECONDS)

        Mockito.verify(observer, Mockito.times(2)).onChanged(any())
        Assert.assertEquals(viewModel!!.networkState.value?.status, Status.SUCCESS)
        Assert.assertEquals(viewModel!!.networkState.value?.msg, NetworkState.MSG_SUCCESS)
    }

    @After
    fun releaseDependencies() {
        ts = null
        tsp = null
        viewModel = null
    }
}