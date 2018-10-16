package com.zenjob.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.zenjob.ui.home.offerdecline.OfferDeclineViewModel
import com.zenjob.ui.home.offerdetails.OfferDetailsViewModel
import com.zenjob.ui.home.offers.OffersViewModel
import com.zenjob.ui.login.loginscreen.LoginViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by suyashg
 *
 * Viewmodel provider module
 */
@Module
interface ViewModelModule {

    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    fun bindLoginViewModel(viewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(OffersViewModel::class)
    fun bindOffersViewModel(viewModel: OffersViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(OfferDetailsViewModel::class)
    fun bindOfferDetailsViewModel(viewModel: OfferDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(OfferDeclineViewModel::class)
    fun bindOfferDeclineViewModel(viewModel: OfferDeclineViewModel): ViewModel

}