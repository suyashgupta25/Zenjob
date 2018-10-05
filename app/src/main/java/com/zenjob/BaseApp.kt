package com.zenjob

import android.content.Context
import com.zenjob.di.DaggerAppComponent
import com.zenjob.di.NetworkModule
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication


open class BaseApp : DaggerApplication() {

    lateinit var androidInjector: AndroidInjector<out DaggerApplication>

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        androidInjector = DaggerAppComponent.builder()
                .application(this)
                .network(networkModule())
                .build()
    }

    public override fun applicationInjector(): AndroidInjector<out DaggerApplication> = androidInjector

    protected open fun networkModule(): NetworkModule = NetworkModule()

}