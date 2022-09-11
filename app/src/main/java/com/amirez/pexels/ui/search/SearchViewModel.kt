package com.amirez.pexels.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amirez.pexels.data.PhotoState
import com.amirez.pexels.data.PhotosData
import com.amirez.pexels.data.repository.SearchRepository
import com.amirez.pexels.data.repository.SearchRepositoryImpl
import com.amirez.pexels.utils.NetworkChecker
import com.amirez.pexels.utils.Resource
import com.amirez.pexels.utils.UIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: SearchRepository
) : ViewModel() {

    private val _photoState = MutableLiveData<PhotoState<List<PhotosData.Photo>>>(
        PhotoState(
            emptyList()
        )
    )
    val photoState: LiveData<PhotoState<List<PhotosData.Photo>>> get() = _photoState

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow: SharedFlow<UIEvent> get() = _eventFlow.asSharedFlow()

    private val _searchQuery = MutableLiveData("")
    val searchQuery: LiveData<String> get() = _searchQuery

    private val arrayOfPhotos = arrayListOf<PhotosData.Photo>()
    private var page = 1
    private var searchJob: Job? = null

    private var _isTyping: Boolean = false
    val isTyping: Boolean get() = _isTyping

    fun changeTypingStatus(isTyping: Boolean) {
        if (isTyping) {
            arrayOfPhotos.clear()
            page = 1
            _isTyping = true
        } else
            _isTyping = false
    }

    fun getPhotos(query: String) {
        _searchQuery.value = query
        searchJob?.cancel()
        if(query.isBlank())
            return
        searchJob = viewModelScope.launch {
            delay(500L)
            repository.getSearchedPhotos(query, page).onEach { event ->
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

    fun cancelPreviousSearchJob() {
        searchJob?.cancel()
        _photoState.postValue(
            photoState.value?.copy(
                data = emptyList(),
                isLoading = false
            )
        )
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