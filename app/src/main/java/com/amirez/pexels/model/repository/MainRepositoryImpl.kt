package com.amirez.pexels.model.repository

import com.amirez.pexels.model.CollectionsData
import com.amirez.pexels.model.network.ApiService
import com.amirez.pexels.model.PhotosData
import retrofit2.Response
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : MainRepository {

    override suspend fun requestPagePhotos(page: Int): Response<PhotosData> {
        return apiService.requestPagePhotos(page)
    }

    override suspend fun requestSearchedPhotos(searchKey: String, page: Int): Response<PhotosData> {
        return apiService.requestSearchedPhotos(searchKey, page)
    }

    override suspend fun requestCollections(page: Int): Response<CollectionsData> {
        return apiService.requestCollections(page)
    }

}