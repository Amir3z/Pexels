package com.amirez.pexels.ui.explore

import com.amirez.pexels.model.CollectionsData
import com.amirez.pexels.model.PhotosData

interface ExploreClickEvents {
    fun onPhotoClick(photo: PhotosData.Photo, position: Int)
    fun onPhotoLongClick(photo: PhotosData.Photo)
    fun onCollectionClick(collectionsData: CollectionsData)
}