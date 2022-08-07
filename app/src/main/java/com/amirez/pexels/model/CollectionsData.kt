package com.amirez.pexels.model


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class CollectionsData(
    @SerializedName("collections")
    val collections: List<Collection>
) : Parcelable {
    @Parcelize
    data class Collection(
        @SerializedName("description")
        val description: String,
        @SerializedName("id")
        val id: String,
        @SerializedName("media_count")
        val mediaCount: Int,
        @SerializedName("photos_count")
        val photosCount: Int,
        @SerializedName("private")
        val `private`: Boolean,
        @SerializedName("title")
        val title: String,
        @SerializedName("videos_count")
        val videosCount: Int
    ) : Parcelable
}