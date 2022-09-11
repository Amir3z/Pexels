package com.amirez.pexels.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amirez.pexels.databinding.ItemPhotoBinding
import com.amirez.pexels.data.PhotosData
import com.bumptech.glide.RequestManager

class SearchAdapter(
    private val glide: RequestManager,
    private val clickEvents: SearchClickEvents,
) :
    RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    private lateinit var binding: ItemPhotoBinding
    private val data = arrayListOf<PhotosData.Photo>()

    inner class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setOnClicks(photo: PhotosData.Photo) {
            itemView.setOnClickListener {
                clickEvents.onPhotoClick(photo)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        binding = ItemPhotoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SearchViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val photo = data[position]
        holder.setOnClicks(photo)

        glide.load(photo.src.medium).into(binding.imgItem)
    }

    fun setData(listOfPhotos: List<PhotosData.Photo>) {
        val position = data.size
        data.addAll(listOfPhotos)
        notifyItemRangeInserted(position, listOfPhotos.size)
    }

    fun clearAndSetNewData(listOfPhotos: List<PhotosData.Photo>) {
        clearAllData()
        data.addAll(listOfPhotos)
        notifyItemRangeInserted(0, listOfPhotos.size)
    }

    fun clearAllData() {
        if (data.isNotEmpty()) {
            val count = data.size
            data.clear()
            notifyItemRangeRemoved(0, count)
        }
    }

    override fun getItemCount(): Int = data.size

    interface SearchClickEvents {
        fun onPhotoClick(photo: PhotosData.Photo)
    }
}