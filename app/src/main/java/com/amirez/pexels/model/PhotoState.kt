package com.amirez.pexels.model

data class PhotoState<T>(
    val data: T,
    val isLoading: Boolean = false
)