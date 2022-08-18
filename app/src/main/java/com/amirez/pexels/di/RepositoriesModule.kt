package com.amirez.pexels.di

import com.amirez.pexels.model.network.ApiService
import com.amirez.pexels.model.repository.CollectionRepository
import com.amirez.pexels.model.repository.ExploreRepository
import com.amirez.pexels.model.repository.SearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoriesModule {

    @Singleton
    @Provides
    fun provideExploreRepository(apiService: ApiService): ExploreRepository {
        return ExploreRepository(apiService)
    }

    @Singleton
    @Provides
    fun provideSearchRepository(apiService: ApiService): SearchRepository {
        return SearchRepository(apiService)
    }

    @Singleton
    @Provides
    fun provideCollectionRepository(apiService: ApiService): CollectionRepository {
        return CollectionRepository(apiService)
    }

}