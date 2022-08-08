package com.amirez.pexels.di

import com.amirez.pexels.ui.explore.*
import com.amirez.pexels.utils.collectionsData
import com.bumptech.glide.RequestManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RecyclerViewModule {

    @Provides
    @Singleton
    fun provideExploreClickEvents(): ExploreClickEvents {
        return ExploreFragment()
    }

    @Provides
    fun providePhotoAdapter(
        requestManager: RequestManager,
        clickEvent: ExploreClickEvents
    ): PhotoAdapter {
        return PhotoAdapter(requestManager, clickEvent)
    }

    @Provides
    @Singleton
    fun provideCollectionAdapter(
        requestManager: RequestManager,
        clickEvent: ExploreClickEvents
    ):CollectionAdapter{
        return CollectionAdapter(requestManager, clickEvent)
    }

}