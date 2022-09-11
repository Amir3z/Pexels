package com.amirez.pexels.ui.explore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amirez.pexels.data.PhotoState
import com.amirez.pexels.data.PhotosData
import com.amirez.pexels.data.repository.ExploreRepository
import com.amirez.pexels.data.repository.ExploreRepositoryImpl
import com.amirez.pexels.utils.NetworkChecker
import com.amirez.pexels.utils.Resource
import com.amirez.pexels.utils.UIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val repository: ExploreRepository
) : ViewModel() {

    private val _photoState = MutableLiveData<PhotoState<List<PhotosData.Photo>>>(
        PhotoState(
            emptyList()
        )
    )
    val photoState: LiveData<PhotoState<List<PhotosData.Photo>>> get() = _photoState

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow: SharedFlow<UIEvent> get() = _eventFlow.asSharedFlow()

    private val arrayOfPhotos = arrayListOf<PhotosData.Photo>()
    private var page = 1

    init {
        getPhotos()
    }


    fun getPhotos() {
        viewModelScope.launch {
            repository.getPagePhotos(page).onEach { event ->
                when (event) {
                    is Resource.Success -> {
                        page++
                        _photoState.postValue(
                            photoState.value?.copy(
                                data = event.data?.photos ?: emptyList(),
                                isLoading = false
                            )
                        )
                        arrayOfPhotos.addAll(event.data?.photos ?: emptyList())
                    }
                    is Resource.Loading -> {
                        _photoState.postValue(
                            photoState.value?.copy(
                                data = emptyList(),
                                isLoading = true
                            )
                        )
                    }
                    is Resource.Failed -> {
                        _photoState.postValue(
                            photoState.value?.copy(
                                data = emptyList(),
                                isLoading = false
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
                data = arrayOfPhotos,
                isLoading = false
            )
        )
    }
}