package com.amirez.pexels.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amirez.pexels.databinding.ItemPhotoBinding
import com.amirez.pexels.model.Collection
import com.bumptech.glide.RequestManager

class CollectionAdapter(
    private val glide: RequestManager,
    private val clickEvents: CollectionsClickEvents
): RecyclerView.Adapter<CollectionAdapter.CollectionViewHolder>() {

    private lateinit var binding: ItemPhotoBinding
    private val data = arrayListOf<Collection.Media>()

    inner class CollectionViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun setOnClicks(photo: Collection.Media) {
            itemView.setOnClickListener {
                clickEvents.onCollectionClick(photo)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionViewHolder {
        binding = ItemPhotoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CollectionViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: CollectionViewHolder, position: Int) {

        val photo = data[position]
        holder.setOnClicks(photo)

        glide.load(photo.src?.medium)
            .into(binding.imgItem)

    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(listOfPhotos: List<Collection.Media>) {
        val newData = arrayListOf<Collection.Media>()
        newData.addAll(data)
        newData.addAll(listOfPhotos)
        data.addAll(newData.distinct())
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int  = data.size


    interface CollectionsClickEvents {
       fun onCollectionClick(collections: Collection.Media)
    }
}