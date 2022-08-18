package com.amirez.pexels.model.network

import com.amirez.pexels.model.dataclass.Collection
import com.amirez.pexels.model.dataclass.PhotosData
import com.amirez.pexels.utils.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @Headers("Authorization: $API_KEY")
    @GET("curated")
    suspend fun requestPagePhotos(
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 30
    ): Response<PhotosData>


    @Headers("Authorization: $API_KEY")
    @GET("search")
    suspend fun requestSearchedPhotos(
        @Query("query") searchKey: String,
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 30
    ): Response<PhotosData>

    @Headers("Authorization: $API_KEY")
    @GET("collections/{id}")
    suspend fun requestCollection(
        @Path("id") id: String,
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 30
    ):Response<Collection>
}