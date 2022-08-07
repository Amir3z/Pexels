package com.amirez.pexels.ui.collections

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amirez.pexels.databinding.ItemCollectionBinding
import com.amirez.pexels.model.CollectionsData

class CollectionAdapter(
    private val collectionEvent: CollectionEvent
) : RecyclerView.Adapter<CollectionAdapter.CollectionViewHolder>() {

    private lateinit var binding: ItemCollectionBinding
    private val data = arrayListOf<CollectionsData.Collection>()

    inner class CollectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setOnClicks(collection: CollectionsData.Collection) {
            itemView.setOnClickListener {
                collectionEvent.onCollectionClick(collection)
            }
            itemView.setOnLongClickListener {
                collectionEvent.onCollectionLongClick(collection)
                true
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
        bindViews(collection)
    }

    private fun bindViews(collection: CollectionsData.Collection) {
        binding.tvTitleCollection.text = collection.title
        binding.tvPhotosCount.text = "${collection.photosCount} Photos"
    }

    override fun getItemCount(): Int = data.size

    fun setData(listOfCollections: List<CollectionsData.Collection>) {
        val position = data.size
        data.addAll(listOfCollections)
        notifyItemRangeInserted(position, listOfCollections.size)

    }

    interface CollectionEvent {
        fun onCollectionClick(collection: CollectionsData.Collection)
        fun onCollectionLongClick(collection: CollectionsData.Collection)
    }
}