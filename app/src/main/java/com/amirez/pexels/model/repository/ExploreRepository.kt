package com.amirez.pexels.model.repository

import com.amirez.pexels.model.network.ApiService
import com.amirez.pexels.model.dataclass.PhotosData
import retrofit2.Response

class ExploreRepository (
    private val apiService: ApiService
) {

    suspend fun requestPagePhotos(page: Int): Response<PhotosData> {
        return apiService.requestPagePhotos(page)
    }

}