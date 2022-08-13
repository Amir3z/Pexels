package com.amirez.pexels.ui.explore.collection

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amirez.pexels.databinding.ItemCollectionBinding
import com.amirez.pexels.model.CollectionsData
import com.amirez.pexels.ui.explore.ClickEvents
import com.bumptech.glide.RequestManager

class CollectionViewPagerAdapter(
    private val glide: RequestManager,
    private val clickEvent: ClickEvents
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

//        val img = when(position) {
//            0 -> R.drawable.img_collection_cars
//            1 -> R.drawable.img_collection_space
//            2 -> R.drawable.img_collection_animals
//            3 -> R.drawable.img_collection_pastel
//            4 -> R.drawable.img_collection_tulips
//            5 -> R.drawable.img_collection_art
//            6 -> R.drawable.img_collection_technology
//            7 -> R.drawable.img_collection_nature
//            8 -> R.drawable.img_collection_coding
//            9 -> R.drawable.img_collection_coffee
//            else -> R.drawable.ic_github
//        }

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