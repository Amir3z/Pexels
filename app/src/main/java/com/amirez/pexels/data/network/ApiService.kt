package com.amirez.pexels.data.network

import com.amirez.pexels.data.Collection
import com.amirez.pexels.data.PhotosData
import com.amirez.pexels.utils.API_KEY
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @Headers("Authorization: $API_KEY")
    @GET("curated")
    suspend fun getPagePhotos(
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 30
    ): PhotosData


    @Headers("Authorization: $API_KEY")
    @GET("search")
    suspend fun getSearchedPhotos(
        @Query("query") searchKey: String,
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 30
    ): PhotosData

    @Headers("Authorization: $API_KEY")
    @GET("collections/{id}")
    suspend fun getCollection(
        @Path("id") id: String,
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 30
    ): Collection
}