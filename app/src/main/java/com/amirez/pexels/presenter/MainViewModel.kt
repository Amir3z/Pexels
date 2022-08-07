package com.amirez.pexels.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amirez.pexels.model.CollectionsData
import com.amirez.pexels.model.PhotosData
import com.amirez.pexels.model.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository,
) : ViewModel() {

    private val _photosLiveData = MutableLiveData<PhotosData>()
    val photosLiveData: LiveData<PhotosData> get() = _photosLiveData

    private val _errorsLiveData = MutableLiveData<String>()
    val errorsLiveData: LiveData<String> get() = _errorsLiveData

    private val _searchedPhotosLiveData = MutableLiveData<PhotosData>()
    val searchedPhotosLiveData: LiveData<PhotosData> get() = _searchedPhotosLiveData

    private val _collectionsLiveData = MutableLiveData<CollectionsData>()
    val collectionsLiveData: LiveData<CollectionsData> get() = _collectionsLiveData


    init {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.requestPagePhotos(1)
            if (response.isSuccessful)
                _photosLiveData.postValue(response.body())
            else
                _errorsLiveData.postValue(response.message())
        }
    }

    fun getPhotosByPage(page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.requestPagePhotos(page)
            if (response.isSuccessful)
                _photosLiveData.postValue(response.body())
            else
                _errorsLiveData.postValue(response.message())
        }
    }

    fun getPhotosBySearchKey(searchKey: String, page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.requestSearchedPhotos(searchKey, page)
            if (response.isSuccessful)
                _searchedPhotosLiveData.postValue(response.body())
            else
                _errorsLiveData.postValue(response.message())
        }
    }

    fun getFeaturedCollections(page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.requestCollections(page)

            if (response.isSuccessful)
                _collectionsLiveData.postValue(response.body())
            else
                _errorsLiveData.postValue(response.message())
        }
    }

}