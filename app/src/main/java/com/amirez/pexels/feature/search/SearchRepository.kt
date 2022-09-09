package com.amirez.pexels.feature.search

import com.amirez.pexels.model.PhotosData
import com.amirez.pexels.model.network.ApiService
import com.amirez.pexels.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class SearchRepository(
    private val apiService: ApiService,
) {

    fun getSearchedPhotos(searchKey: String, page: Int): Flow<Resource<PhotosData>> = flow {
        emit(Resource.Loading())

        try {
            val response = apiService.getSearchedPhotos(searchKey, page)
            emit(Resource.Success(response))
        } catch (ex: HttpException) {
            if (ex.code() == 403)
                emit(Resource.Failed(message = "Turn on your VPN."))
            else
                emit(Resource.Failed(message = ex.message() ?: "Something happened!"))
        } catch (ex: IOException) {
            emit(Resource.Failed(message = "Check your connection!"))
        }
    }

}