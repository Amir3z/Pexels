package com.amirez.pexels.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amirez.pexels.model.dataclass.Collection
import com.amirez.pexels.model.repository.CollectionRepository
import com.amirez.pexels.utils.NetworkChecker
import com.amirez.pexels.utils.filterPhotos
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionViewModel @Inject constructor(
    private val repository: CollectionRepository,
    private val networkChecker: NetworkChecker
): ViewModel() {

    private val _collectionLiveData = MutableLiveData<List<Collection.Media>>()
    val collectionLiveData: LiveData<List<Collection.Media>> get() = _collectionLiveData

    private val _errorsLiveData = MutableLiveData<String>()
    val errorsLiveData: LiveData<String> get() = _errorsLiveData

    private val _isConnected = MutableLiveData<Boolean>()
    val isConnected: LiveData<Boolean> get() = _isConnected

    private val arrayOfPhotos = arrayListOf<Collection.Media>()
    private var page = 1

    fun getFirstPagePhotos(id:String) {
        if (collectionLiveData.value != null)
            return  //makes sure if this is the first time requesting first page

        if(networkChecker.isConnected){
            _isConnected.postValue(true)

            viewModelScope.launch(Dispatchers.IO) {
                val response = repository.requestCollectionPhotos(id, 1)
                if (response.isSuccessful) {
                    val newData = response.body()!!.media.filterPhotos()
                    _collectionLiveData.postValue(newData)
                    arrayOfPhotos.addAll(newData)
                } else
                    _errorsLiveData.postValue(response.message())
            }
        }
        else
            _isConnected.postValue(false)
    }

    fun getNextPagePhotos(id: String) {
        if(networkChecker.isConnected){
            _isConnected.postValue(true)

            viewModelScope.launch(Dispatchers.IO) {
                val response = repository.requestCollectionPhotos(id, ++page)
                if (response.isSuccessful) {
                    val newData = response.body()!!.media.filterPhotos()
                    _collectionLiveData.postValue(newData)
                    arrayOfPhotos.addAll(newData)
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
        _collectionLiveData.postValue(arrayOfPhotos)
    }

}