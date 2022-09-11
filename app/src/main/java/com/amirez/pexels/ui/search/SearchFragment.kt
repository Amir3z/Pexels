package com.amirez.pexels.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.amirez.pexels.databinding.FragmentSearchBinding
import com.amirez.pexels.data.PhotosData
import com.amirez.pexels.data.SafeArgsPhoto
import com.amirez.pexels.utils.UIEvent
import com.amirez.pexels.utils.isSearchKeyValid
import com.bumptech.glide.RequestManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class SearchFragment : Fragment(), SearchAdapter.SearchClickEvents {

    private val viewModel: SearchViewModel by viewModels()
    private lateinit var adapter: SearchAdapter

    @Inject
    lateinit var glideInstance: RequestManager
    private lateinit var binding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        observeLiveData()

        if(binding.etSearch.text.isBlank()) {
            adapter.clearAllData()
            binding.btnLoadMore.visibility = View.GONE
        }
        binding.etSearch.addTextChangedListener {
            if (it.toString().isBlank()) {
                viewModel.cancelPreviousSearchJob()
                adapter.clearAllData()
                binding.btnLoadMore.visibility = View.GONE
            } else {
                viewModel.changeTypingStatus(true)
                getPhotos(it.toString())
            }
        }

        binding.btnLoadMore.setOnClickListener {
            viewModel.changeTypingStatus(false)
            getPhotos(binding.etSearch.text.toString())
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.saveAllOpenPagesInLiveData()
    }

    private fun getPhotos(searchKey: String) {
        if (isSearchKeyValid()) {
            viewModel.getPhotos(searchKey)
        } else {
            adapter.clearAllData()
            binding.btnLoadMore.visibility = View.GONE
        }
    }

    private fun initRecyclerView() {
        adapter = SearchAdapter(glideInstance, this)
        binding.rvSearched.layoutManager =
            StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        binding.rvSearched.adapter = adapter
    }

    private fun observeLiveData() {
        viewModel.photoState.observe(requireActivity()) { state ->
            if (state.isLoading) {
                binding.layoutNoInternet.root.visibility = View.GONE
                binding.progress.visibility = View.VISIBLE
            }
            if (!state.isLoading) {
                binding.progress.visibility = View.INVISIBLE
            }
            if (state.data.isNotEmpty()) {
                showPhotos(state.data)
                binding.btnLoadMore.visibility = View.VISIBLE
            }
        }

        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.eventFlow.collectLatest { event ->
                when (event) {
                    is UIEvent.ShowAlternativeView -> {
                        showAlternativeView(event.message)
                    }
                }
            }
        }

    }

    private fun showAlternativeView(message: String) {
        binding.layoutNoInternet.root.visibility = View.VISIBLE
        binding.layoutNoInternet.tvMessage.text = message
        binding.layoutNoInternet.btnRetry.setOnClickListener {
            viewModel.getPhotos(viewModel.searchQuery.value ?: "")
        }
    }

    private fun showPhotos(photos: List<PhotosData.Photo>) {
        if (viewModel.isTyping) {
            adapter.clearAndSetNewData(photos)
            binding.btnLoadMore.visibility = View.VISIBLE
        } else {
            adapter.setData(photos)
        }
    }

    override fun onPhotoClick(photo: PhotosData.Photo) {
        val data = SafeArgsPhoto(
            photo.photographer,
            photo.alt,
            photo.id.toString(),
            photo.src.large2x,
            photo.url,
            photo.avgColor
        )
        findNavController().navigate(
            SearchFragmentDirections.actionSearchFragmentToPhotoDetailsFragment(data)
        )
    }

}