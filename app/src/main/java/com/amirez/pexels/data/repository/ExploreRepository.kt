package com.amirez.pexels.data.repository

import com.amirez.pexels.data.PhotosData
import com.amirez.pexels.utils.Resource
import kotlinx.coroutines.flow.Flow

interface ExploreRepository {
    fun getPagePhotos(page: Int): Flow<Resource<PhotosData>>
}