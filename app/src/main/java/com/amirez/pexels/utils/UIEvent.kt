package com.amirez.pexels.utils

sealed class UIEvent {
    data class ShowAlternativeView(val message: String) : UIEvent()
}
