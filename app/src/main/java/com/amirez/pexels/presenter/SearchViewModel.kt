package com.amirez.pexels.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amirez.pexels.model.dataclass.PhotosData
import com.amirez.pexels.model.repository.SearchRepository
import com.amirez.pexels.utils.NetworkChecker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: SearchRepository,
    private val networkChecker: NetworkChecker
):ViewModel() {

    private val _errorsLiveData = MutableLiveData<String>()
    val errorsLiveData: LiveData<String> get() = _errorsLiveData

    private val _searchedPhotosLiveData = MutableLiveData<List<PhotosData.Photo>>()
    val searchedPhotosLiveData: LiveData<List<PhotosData.Photo>> get() = _searchedPhotosLiveData

    private val _isConnected = MutableLiveData<Boolean>()
    val isConnected: LiveData<Boolean> get() = _isConnected

    private val arrayOfPhotos = arrayListOf<PhotosData.Photo>()
    private var page = 1

    private var _isTyping: Boolean = false
    val isTyping: Boolean get() = _isTyping

    fun changeTypingStatus(status: Boolean) {
        if(status) {
            arrayOfPhotos.clear()
            page = 1
            _isTyping = true
        } else
            _isTyping = false
    }

    fun getPhotos(key: String) {

        if(networkChecker.isConnected){
            _isConnected.postValue(true)

            viewModelScope.launch(Dispatchers.IO) {
                val response = repository.requestSearchedPhotos(key,page++)
                if (response.isSuccessful) {
                    _searchedPhotosLiveData.postValue(response.body()!!.photos)
                    arrayOfPhotos.addAll(response.body()!!.photos)
                }
                else
                    _errorsLiveData.postValue(response.message())
            }
        }
        else
            _isConnected.postValue(false)
    }

    fun getNextPagePhotos(key: String) {
        if(networkChecker.isConnected){
            _isConnected.postValue(true)

            viewModelScope.launch(Dispatchers.IO) {
                val response = repository.requestSearchedPhotos(key,++page)
                if (response.isSuccessful) {
                    _searchedPhotosLiveData.postValue(response.body()!!.photos)
                    arrayOfPhotos.addAll(response.body()!!.photos)
                }
                else {
                    page--
                    _errorsLiveData.postValue(response.message())
                }
            }
        }
        else
            _isConnected.postValue(false)
    }

    fun saveAllOpenPagesInLiveData() {
        _searchedPhotosLiveData.postValue(arrayOfPhotos)
    }

}