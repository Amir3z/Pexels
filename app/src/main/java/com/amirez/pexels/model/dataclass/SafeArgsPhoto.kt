package com.amirez.pexels.model.dataclass

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SafeArgsPhoto(
    val photographer: String,
    val alt: String,
    val id: String,
    val src: String,
    val url: String,
    val avgColor: String
):Parcelable
