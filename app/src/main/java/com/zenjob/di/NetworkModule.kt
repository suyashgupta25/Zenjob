package com.zenjob.di

import android.content.Intent
import android.content.SharedPreferences
import com.squareup.moshi.Moshi
import com.zenjob.BaseApp
import com.zenjob.BuildConfig
import com.zenjob.data.local.PreferenceHelper.KEY_AUTH_TOKEN
import com.zenjob.data.local.PreferenceHelper.get
import com.zenjob.data.local.PreferenceHelper.set
import com.zenjob.data.remote.ZenjobService
import com.zenjob.ui.login.LoginActivity
import com.zenjob.utils.AppConstants.Companion.CONNECTION_TIMEOUT
import com.zenjob.utils.AppConstants.Companion.EMPTY
import com.zenjob.utils.AppConstants.Companion.READ_TIMEOUT
import com.zenjob.utils.AppConstants.Companion.WRITE_TIMEOUT
import com.zenjob.utils.ApplicationJsonAdapterFactory
import com.zenjob.utils.InstantAdapter
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
open class NetworkModule {

    open fun buildOkHttpClient(app: BaseApp, prefs: SharedPreferences): OkHttpClient =
            OkHttpClient.Builder()
                    .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                    .addInterceptor(HeadersInterceptor(prefs, app))
                    .build()

    @Provides
    @Singleton
    fun provideOkHttpClient(app: BaseApp, prefs: SharedPreferences): OkHttpClient = buildOkHttpClient(app, prefs)

    @Singleton
    @Provides
    fun provideMoshi() = Moshi.Builder()
            .add(ApplicationJsonAdapterFactory.INSTANCE)
            .add(InstantAdapter.INSTANCE)
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideZenjobService(retrofit: Retrofit): ZenjobService =
            retrofit.create(ZenjobService::class.java)

    private class HeadersInterceptor constructor(val prefs: SharedPreferences, val app: BaseApp) : Interceptor {

        override fun intercept(chain: Interceptor.Chain): Response {
            val original = chain.request()

            // Request customization: add request headers
            val requestBuilder = original.newBuilder()
                    .addHeader("Authorization", "Bearer " + prefs[KEY_AUTH_TOKEN, EMPTY])

            val request = requestBuilder.build()
            val response = chain.proceed(request)
            if (!response.isSuccessful && response.code() == 401) {
                prefs[KEY_AUTH_TOKEN] = EMPTY
                val intent = Intent(app.applicationContext, LoginActivity::class.java)
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                app.applicationContext.startActivity(intent)
            }
            return response
        }
    }

}