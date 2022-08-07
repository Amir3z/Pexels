package com.amirez.pexels.di

import com.amirez.pexels.ui.collections.CollectionAdapter
import com.amirez.pexels.ui.collections.CollectionsFragment
import com.amirez.pexels.ui.explore.PhotoAdapter
import com.amirez.pexels.ui.explore.ExploreFragment
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
    fun providePhotoEvent(): PhotoAdapter.PhotoEvent {
        return ExploreFragment()
    }

    @Provides
    fun providePhotoAdapter(
        requestManager: RequestManager,
        photoEvent: PhotoAdapter.PhotoEvent,
    ): PhotoAdapter {
        return PhotoAdapter(requestManager, photoEvent)
    }

    @Provides
    @Singleton
    fun provideCollectionEvent(): CollectionAdapter.CollectionEvent {
        return CollectionsFragment()
    }

    @Singleton
    @Provides
    fun provideCollectionAdapter(
        collectionEvent: CollectionAdapter.CollectionEvent,
    ): CollectionAdapter {
        return CollectionAdapter(collectionEvent)
    }

}