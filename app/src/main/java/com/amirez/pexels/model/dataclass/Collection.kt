package com.amirez.pexels.model.dataclass


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class Collection(
    @SerializedName("id")
    val id: String,
    @SerializedName("media")
    val media: List<Media>,
    @SerializedName("next_page")
    val nextPage: String,
    @SerializedName("page")
    val page: Int,
    @SerializedName("per_page")
    val perPage: Int,
    @SerializedName("total_results")
    val totalResults: Int
) : Parcelable {
    @Parcelize
    data class Media(
        @SerializedName("alt")
        val alt: String?,
        @SerializedName("avg_color")
        val avgColor: String?,
        @SerializedName("duration")
        val duration: Int?,
        @SerializedName("height")
        val height: Int,
        @SerializedName("id")
        val id: Int,
        @SerializedName("image")
        val image: String?,
        @SerializedName("liked")
        val liked: Boolean?,
        @SerializedName("photographer")
        val photographer: String?,
        @SerializedName("photographer_id")
        val photographerId: Int?,
        @SerializedName("photographer_url")
        val photographerUrl: String?,
        @SerializedName("src")
        val src: Src?,
        @SerializedName("type")
        val type: String,
        @SerializedName("url")
        val url: String,
        @SerializedName("user")
        val user: User?,
        @SerializedName("video_files")
        val videoFiles: List<VideoFile>?,
        @SerializedName("video_pictures")
        val videoPictures: List<VideoPicture>?,
        @SerializedName("width")
        val width: Int
    ) : Parcelable {
        @Parcelize
        data class Src(
            @SerializedName("landscape")
            val landscape: String,
            @SerializedName("large")
            val large: String,
            @SerializedName("large2x")
            val large2x: String,
            @SerializedName("medium")
            val medium: String,
            @SerializedName("original")
            val original: String,
            @SerializedName("portrait")
            val portrait: String,
            @SerializedName("small")
            val small: String,
            @SerializedName("tiny")
            val tiny: String
        ) : Parcelable

        @Parcelize
        data class User(
            @SerializedName("id")
            val id: Int,
            @SerializedName("name")
            val name: String,
            @SerializedName("url")
            val url: String
        ) : Parcelable

        @Parcelize
        data class VideoFile(
            @SerializedName("file_type")
            val fileType: String,
            @SerializedName("height")
            val height: Int,
            @SerializedName("id")
            val id: Int,
            @SerializedName("link")
            val link: String,
            @SerializedName("quality")
            val quality: String,
            @SerializedName("width")
            val width: Int
        ) : Parcelable

        @Parcelize
        data class VideoPicture(
            @SerializedName("id")
            val id: Int,
            @SerializedName("nr")
            val nr: Int,
            @SerializedName("picture")
            val picture: String
        ) : Parcelable
    }
}