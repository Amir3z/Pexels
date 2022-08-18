package com.amirez.pexels.model.repository

import com.amirez.pexels.model.dataclass.PhotosData
import com.amirez.pexels.model.network.ApiService
import retrofit2.Response

class SearchRepository(
    private val apiService: ApiService,
) {

    suspend fun requestSearchedPhotos(searchKey: String, page: Int): Response<PhotosData> {
        return apiService.requestSearchedPhotos(searchKey, page)
    }

}