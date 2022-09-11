package com.amirez.pexels.data

data class PhotoState<T>(
    val data: T,
    val isLoading: Boolean = false
)