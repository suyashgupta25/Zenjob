package com.zenjob.di

import android.arch.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module

/**
 * Created by suyashg
 *
 * Viewmodel provider module
 */
@Module
interface ViewModelModule {

    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory


}