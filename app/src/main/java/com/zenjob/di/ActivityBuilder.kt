package com.zenjob.di

import com.zenjob.ui.home.HomeActivity
import com.zenjob.ui.login.LoginActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [MainModule::class])
    internal abstract fun contributeLoginInjector(): LoginActivity

    @ContributesAndroidInjector(modules = [MainModule::class])
    internal abstract fun contributeHomeInjector(): HomeActivity

}