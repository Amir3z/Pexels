package com.amirez.pexels.ui.collections

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.amirez.pexels.databinding.FragmentCollectionsBinding
import com.amirez.pexels.model.CollectionsData
import com.amirez.pexels.presenter.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CollectionsFragment: Fragment(), CollectionAdapter.CollectionEvent {
    private lateinit var binding: FragmentCollectionsBinding
    private val viewModel: MainViewModel by activityViewModels()
    @Inject
    lateinit var adapter: CollectionAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCollectionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeOnLiveData()
        initRecyclerView()
        viewModel.getFeaturedCollections(1)
    }

    private fun observeOnLiveData() {
        viewModel.collectionsLiveData.observe(requireActivity()) {
            adapter.setData(it.collections)
            it.collections.forEach { that ->
                Log.d("tagx", "observeOnLiveData: ${that.title}")
            }
        }
        viewModel.errorsLiveData.observe(requireActivity()) {

        }
    }

    private fun initRecyclerView() {
        binding.rvCollections.adapter = adapter
        binding.rvCollections.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onCollectionClick(collection: CollectionsData.Collection) {

    }

    override fun onCollectionLongClick(collection: CollectionsData.Collection) {

    }
}