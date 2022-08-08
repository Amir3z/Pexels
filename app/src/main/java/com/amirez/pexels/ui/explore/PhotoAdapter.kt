package com.amirez.pexels.ui.explore

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amirez.pexels.databinding.ItemPhotoBinding
import com.amirez.pexels.model.PhotosData
import com.bumptech.glide.RequestManager

class PhotoAdapter(
    private val glide: RequestManager,
    private val clickEvent: ExploreClickEvents,
) :
    RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {

    private lateinit var binding: ItemPhotoBinding
    private val data = arrayListOf<PhotosData.Photo>()

    inner class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setOnClicks(photo: PhotosData.Photo, position: Int) {
            itemView.setOnClickListener {
                clickEvent.onPhotoClick(photo, position)
            }

            itemView.setOnLongClickListener {
                clickEvent.onPhotoLongClick(photo)
                true
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
        bindViews(photo)
        holder.setOnClicks(photo, position)
    }

    override fun getItemCount(): Int = data.size

    private fun bindViews(photo: PhotosData.Photo) {
        glide.load(photo.src.medium)
            .into(binding.imgItem)
    }

    fun setData(listOfPhotos: List<PhotosData.Photo>) {
        val position = data.size
        data.addAll(listOfPhotos)
        notifyItemRangeInserted(position, listOfPhotos.size)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearAndSetNewData(listOfPhotos: List<PhotosData.Photo>) {
//        val position = data.size
        data.clear()
        data.addAll(listOfPhotos)
//        notifyItemRangeRemoved(0, position)
//        notifyItemRangeInserted(0, listOfPhotos.size)
        notifyDataSetChanged()
    }

    fun clearAllData() {
        if(data.isNotEmpty()) {
            val count = data.size
            data.clear()
            notifyItemRangeRemoved(0, count)
        }
    }
}