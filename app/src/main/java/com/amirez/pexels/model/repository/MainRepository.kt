package com.amirez.pexels.model.repository

import com.amirez.pexels.model.PhotosData
import retrofit2.Response

interface MainRepository {

    suspend fun requestPagePhotos(page: Int): Response<PhotosData>

    suspend fun requestSearchedPhotos(searchKey: String, page: Int): Response<PhotosData>

}