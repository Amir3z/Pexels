package com.amirez.pexels.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amirez.pexels.model.dataclass.PhotosData
import com.amirez.pexels.model.repository.ExploreRepository
import com.amirez.pexels.utils.NetworkChecker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val repository: ExploreRepository,
    private val networkChecker: NetworkChecker
): ViewModel() {

    private val _photosLiveData = MutableLiveData<List<PhotosData.Photo>>()
    val photosLiveData: LiveData<List<PhotosData.Photo>> get() = _photosLiveData

    private val _errorsLiveData = MutableLiveData<String>()
    val errorsLiveData: LiveData<String> get() = _errorsLiveData

    private val _isConnected = MutableLiveData<Boolean>()
    val isConnected: LiveData<Boolean> get() = _isConnected

    private val arrayOfPhotos = arrayListOf<PhotosData.Photo>()
    private var page = 1

    init {
        if(networkChecker.isConnected){
            _isConnected.postValue(true)

            viewModelScope.launch(Dispatchers.IO) {
                val response = repository.requestPagePhotos(1)
                if (response.isSuccessful) {
                    _photosLiveData.postValue(response.body()!!.photos)
                    arrayOfPhotos.addAll(response.body()!!.photos)
                }
                else
                    _errorsLiveData.postValue(response.message())
            }
        }
        else
            _isConnected.postValue(false)
    }


    fun getNextPagePhotos() {
        if(networkChecker.isConnected){
            _isConnected.postValue(true)

            viewModelScope.launch(Dispatchers.IO) {
                val response = repository.requestPagePhotos(++page)
                if (response.isSuccessful) {
                    _photosLiveData.postValue(response.body()!!.photos)
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
        _photosLiveData.postValue(arrayOfPhotos)
    }
}