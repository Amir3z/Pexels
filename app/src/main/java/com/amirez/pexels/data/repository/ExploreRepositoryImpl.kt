package com.amirez.pexels.data.repository

import com.amirez.pexels.data.network.ApiService
import com.amirez.pexels.data.PhotosData
import com.amirez.pexels.utils.NetworkChecker
import com.amirez.pexels.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.internal.notify
import retrofit2.HttpException
import java.io.IOException

class ExploreRepositoryImpl (
    private val apiService: ApiService,
    private val networkChecker: NetworkChecker
): ExploreRepository {

    override fun getPagePhotos(page: Int): Flow<Resource<PhotosData>> = flow {
        emit(Resource.Loading())

        if(!networkChecker.isConnected)
            emit(Resource.Failed(message = "Check your connection!"))

        try {
            val response = apiService.getPagePhotos(page)
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