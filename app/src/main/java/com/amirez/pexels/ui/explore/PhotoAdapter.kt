package com.amirez.pexels.ui.explore

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amirez.pexels.databinding.ItemPhotoBinding
import com.amirez.pexels.data.CollectionsData
import com.amirez.pexels.data.PhotosData
import com.bumptech.glide.RequestManager

class PhotoAdapter(
    private val glide: RequestManager,
    private val clickEvent: ExploreClickEvents,
) :
    RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {

    private lateinit var binding: ItemPhotoBinding
    private val data = arrayListOf<PhotosData.Photo>()

    inner class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setOnClicks(photo: PhotosData.Photo) {
            itemView.setOnClickListener {
                clickEvent.onPhotoClick(photo)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        binding = ItemPhotoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return PhotoViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val photo = data[position]

        glide.load(photo.src.medium)
            .into(binding.imgItem)

        holder.setOnClicks(photo)
    }

    override fun getItemCount(): Int = data.size

    fun setData(listOfPhotos: List<PhotosData.Photo>) {
        val position = data.size
        data.addAll(listOfPhotos)
        notifyItemRangeInserted(position,listOfPhotos.size)
    }

    interface ExploreClickEvents {
        fun onPhotoClick(photo: PhotosData.Photo)
        fun onCollectionClick(collectionsData: CollectionsData)
    }
}