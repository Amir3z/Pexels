package com.amirez.pexels.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.amirez.pexels.databinding.FragmentCollectionBinding
import com.amirez.pexels.model.Collection
import com.amirez.pexels.presenter.CollectionViewModel
import com.bumptech.glide.RequestManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CollectionFragment : Fragment(), CollectionAdapter.CollectionsClickEvents {
    private lateinit var binding: FragmentCollectionBinding
    private val viewModel: CollectionViewModel by viewModels()
    private lateinit var adapter: CollectionAdapter
    private lateinit var id: String
    @Inject lateinit var glideInstance: RequestManager


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentCollectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        id = CollectionFragmentArgs.fromBundle(requireArguments()).collection
        viewModel.getFirstPagePhotosAuto(id)

        initRecyclerView()
        observeLiveData()

        binding.btnLoadMore.setOnClickListener {
            viewModel.getNextPagePhotos(id)
        }

    }

    override fun onDetach() {
        super.onDetach()
        viewModel.saveAllOpenPagesInLiveData()
    }

    private fun initRecyclerView() {
        adapter = CollectionAdapter(glideInstance, this)
        binding.rvCollection.layoutManager =
            StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        binding.rvCollection.adapter = adapter
    }

    private fun observeLiveData() {
        viewModel.collectionLiveData.observe(requireActivity()){
            adapter.setData(it)
            binding.btnLoadMore.visibility = View.VISIBLE
        }
        viewModel.errorsLiveData.observe(requireActivity()) {
            Log.d("tagx", "observeLiveData: $it")
        }
        viewModel.isConnected.observe(requireActivity()) { isConnected ->
            if(isConnected){
                binding.layoutNoInternet.root.visibility = View.GONE
            } else{
                binding.layoutNoInternet.root.visibility = View.VISIBLE
            }
        }
    }

    override fun onCollectionClick(collections: Collection.Media) {
//        findNavController().navigate(
//            CollectionFragmentDirections.actionCollectionFragmentToLargePhotoFragment(collections)
//        )
        Log.d("tagx", "onCollectionClick: ${collections.type}")
    }
}