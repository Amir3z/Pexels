package com.amirez.pexels.di

import android.app.Application
import com.amirez.pexels.R
import com.amirez.pexels.data.network.ApiService
import com.amirez.pexels.data.repository.*
import com.amirez.pexels.utils.NetworkChecker
import com.amirez.pexels.utils.PEXELS_BASE_URL
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRequestOptions(): RequestOptions {
        return RequestOptions
            .placeholderOf(R.drawable.back_image_not_loaded)
    }

    @Provides
    @Singleton
    fun provideGlideInstance(requestOptions: RequestOptions, app: Application): RequestManager {
        return Glide
            .with(app)
            .setDefaultRequestOptions(requestOptions)
    }

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

    @Singleton
    @Provides
    fun provideExploreRepository(apiService: ApiService): ExploreRepository {
        return ExploreRepositoryImpl(apiService)
    }

    @Singleton
    @Provides
    fun provideSearchRepository(apiService: ApiService): SearchRepository {
        return SearchRepositoryImpl(apiService)
    }

    @Singleton
    @Provides
    fun provideCollectionRepository(apiService: ApiService): CollectionRepository {
        return CollectionRepositoryImpl(apiService)
    }
}