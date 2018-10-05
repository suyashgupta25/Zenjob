package com.zenjob.di

import com.squareup.moshi.Moshi
import com.zenjob.BaseApp
import com.zenjob.BuildConfig
import com.zenjob.data.remote.ZenjobService
import com.zenjob.utils.AppConstants.Companion.CONNECTION_TIMEOUT
import com.zenjob.utils.AppConstants.Companion.READ_TIMEOUT
import com.zenjob.utils.AppConstants.Companion.WRITE_TIMEOUT
import com.zenjob.utils.ApplicationJsonAdapterFactory
import com.zenjob.utils.InstantAdapter
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
open class NetworkModule {

    open fun buildOkHttpClient(app: BaseApp): OkHttpClient =
            OkHttpClient.Builder()
                    .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                    .build()

    @Provides
    @Singleton
    fun provideOkHttpClient(app: BaseApp): OkHttpClient = buildOkHttpClient(app)

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

}