package com.amirez.pexels.di

import android.app.Application
import com.amirez.pexels.model.network.ApiService
import com.amirez.pexels.utils.NetworkChecker
import com.amirez.pexels.utils.PEXELS_BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        return Retrofit.Builder()
            .baseUrl(PEXELS_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideNetworkChecker(application: Application): NetworkChecker {
        return NetworkChecker(application.applicationContext)
    }

}