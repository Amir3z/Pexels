package com.amirez.pexels.feature.collection

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amirez.pexels.model.Collection
import com.amirez.pexels.model.PhotoState
import com.amirez.pexels.utils.NetworkChecker
import com.amirez.pexels.utils.Resource
import com.amirez.pexels.utils.UIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionViewModel @Inject constructor(
    private val repository: CollectionRepository,
    private val networkChecker: NetworkChecker
) : ViewModel() {

    private val _photoState = MutableLiveData<PhotoState<List<Collection.Media>>>(
        PhotoState(
            emptyList()
        )
    )
    val photoState: LiveData<PhotoState<List<Collection.Media>>> get() = _photoState

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow: SharedFlow<UIEvent> get() = _eventFlow.asSharedFlow()

    private val arrayOfPhotos = arrayListOf<Collection.Media>()
    private var page = 1
    private var isFirstTime = true

    fun getFirstPagePhotos(id: String) {
        if (!isFirstTime)
            return
        getPhotos(id)
    }

    fun getPhotos(id: String) {
        if (!networkChecker.isConnected) {
            UIEvent.ShowAlternativeView("Check your connection!")
            return
        }
        viewModelScope.launch {
            repository.getCollectionPhotos(id, page).onEach { event ->
                when (event) {
                    is Resource.Success -> {
                        isFirstTime = false
                        page++
                        _photoState.postValue(
                            photoState.value?.copy(
                                isLoading = false,
                                data = event.data?.media ?: emptyList()
                            )
                        )
                        arrayOfPhotos.addAll(event.data?.media ?: emptyList())
                    }
                    is Resource.Loading -> {
                        _photoState.postValue(
                            photoState.value?.copy(
                                isLoading = true,
                                data = emptyList()
                            )
                        )
                    }
                    is Resource.Failed -> {
                        _photoState.postValue(
                            photoState.value?.copy(
                                isLoading = false,
                                data = emptyList()
                            )
                        )
                        _eventFlow.emit(
                            UIEvent.ShowAlternativeView(event.message ?: "Something happened!")
                        )
                    }
                }
            }.launchIn(this)

        }
    }

    fun saveAllOpenPagesInLiveData() {
        _photoState.postValue(
            PhotoState(
                arrayOfPhotos,
                false
            )
        )
    }

}