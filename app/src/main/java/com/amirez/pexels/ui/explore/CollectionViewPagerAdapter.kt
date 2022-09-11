package com.amirez.pexels.ui.explore

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amirez.pexels.databinding.ItemCollectionBinding
import com.amirez.pexels.data.CollectionsData
import com.bumptech.glide.RequestManager

class CollectionViewPagerAdapter(
    private val glide: RequestManager,
    private val clickEvent: PhotoAdapter.ExploreClickEvents
) : RecyclerView.Adapter<CollectionViewPagerAdapter.CollectionViewHolder>() {
    private lateinit var binding: ItemCollectionBinding
    private val data = arrayListOf<CollectionsData>()

    inner class CollectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun setOnClicks(collectionsData: CollectionsData) {
            itemView.setOnClickListener {
                clickEvent.onCollectionClick(collectionsData)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionViewHolder {
        binding = ItemCollectionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CollectionViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: CollectionViewHolder, position: Int) {

        val collection = data[position]
        holder.setOnClicks(collection)

        glide.load(collection.resourceId)
            .into(binding.imgCollection)

    }

    fun setData(listOfPhotos: List<CollectionsData>) {
        val position = data.size
        data.addAll(listOfPhotos)
        notifyItemRangeInserted(position, listOfPhotos.size)
    }

    override fun getItemCount(): Int = data.size

}