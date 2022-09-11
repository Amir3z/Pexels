package com.amirez.pexels.data.repository

import com.amirez.pexels.data.Collection
import com.amirez.pexels.utils.Resource
import kotlinx.coroutines.flow.Flow

interface CollectionRepository {
    fun getCollectionPhotos(id: String, page: Int): Flow<Resource<Collection>>
}