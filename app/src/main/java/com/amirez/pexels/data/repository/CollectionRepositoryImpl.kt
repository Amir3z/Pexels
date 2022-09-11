package com.amirez.pexels.data.repository

import com.amirez.pexels.data.Collection
import com.amirez.pexels.data.network.ApiService
import com.amirez.pexels.utils.NetworkChecker
import com.amirez.pexels.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class CollectionRepositoryImpl(
    private val apiService: ApiService,
    private val networkChecker: NetworkChecker
): CollectionRepository {

    override fun getCollectionPhotos(id: String, page: Int): Flow<Resource<Collection>> = flow {
        emit(Resource.Loading())

        if(!networkChecker.isConnected)
            emit(Resource.Failed(message = "Check your connection!"))

        try {
            val response = apiService.getCollection(id, page)
            emit(Resource.Success(response))
        } catch (ex: HttpException) {
            if (ex.code() == 403)
                emit(Resource.Failed(message = "Turn on your VPN."))
            else
                emit(Resource.Failed(message = ex.message() ?: "Something happened!"))
        } catch (ex: IOException) {
            emit(Resource.Failed(message = ex.message ?: "Something happened!"))
        }
    }

}