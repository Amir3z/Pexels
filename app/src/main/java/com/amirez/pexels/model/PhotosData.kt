package com.amirez.pexels.model


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class PhotosData(
    @SerializedName("photos")
    val photos: List<Photo>,
    @SerializedName("total_results")
    val totalResults: Int
) : Parcelable {
    @Parcelize
    data class Photo(
        @SerializedName("alt")
        val alt: String,
        @SerializedName("avg_color")
        val avgColor: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("photographer")
        val photographer: String,
        @SerializedName("photographer_id")
        val photographerId: Int,
        @SerializedName("photographer_url")
        val photographerUrl: String,
        @SerializedName("src")
        val src: Src,
        @SerializedName("url")
        val url: String,
    ) : Parcelable {
        @Parcelize
        data class Src(
            @SerializedName("large")
            val large: String,
            @SerializedName("large2x")
            val large2x: String,
            @SerializedName("medium")
            val medium: String,
            @SerializedName("original")
            val original: String,
            @SerializedName("small")
            val small: String,
        ) : Parcelable
    }
}