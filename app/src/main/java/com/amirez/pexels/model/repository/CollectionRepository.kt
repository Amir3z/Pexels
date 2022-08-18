package com.amirez.pexels.model.repository

import com.amirez.pexels.model.dataclass.Collection
import com.amirez.pexels.model.network.ApiService
import retrofit2.Response

class CollectionRepository(
    private val apiService: ApiService
) {

    suspend fun requestCollectionPhotos(id: String, page:Int): Response<Collection>{
        return apiService.requestCollection(id, page)
    }

}