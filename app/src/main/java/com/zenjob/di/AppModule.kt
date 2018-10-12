package com.zenjob.di

import android.content.SharedPreferences
import com.zenjob.BaseApp
import com.zenjob.data.local.AppPrefsHelper
import com.zenjob.data.local.PreferenceHelper
import com.zenjob.data.local.PrefsHelper
import com.zenjob.ui.common.dialogs.ErrorDialog
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [NetworkModule::class, ViewModelModule::class])
open class AppModule {

    @Provides
    @Singleton
    open fun providePrefs(app: BaseApp): SharedPreferences = PreferenceHelper.defaultPrefs(app.applicationContext)

    @Provides
    @Singleton
    open fun providePrefsHelper(prefs: SharedPreferences): PrefsHelper = AppPrefsHelper(prefs)

    @Provides
    @Singleton
    open fun provideErrorDialog(): ErrorDialog = ErrorDialog()
}