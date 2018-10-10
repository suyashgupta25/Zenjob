package com.zenjob.di

import com.zenjob.ui.home.offers.OffersFragment
import com.zenjob.ui.login.loginscreen.LoginFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class MainModule {

    @ContributesAndroidInjector
    internal abstract fun contributeLoginFragmentInjector(): LoginFragment

    @ContributesAndroidInjector
    internal abstract fun contributeOffersFragmentInjector(): OffersFragment

}