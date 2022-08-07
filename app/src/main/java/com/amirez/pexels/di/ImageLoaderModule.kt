package com.amirez.pexels.di

import android.app.Application
import com.amirez.pexels.R
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ImageLoaderModule {

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

}