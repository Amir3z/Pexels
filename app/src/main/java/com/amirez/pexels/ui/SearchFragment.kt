package com.amirez.pexels.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.amirez.pexels.databinding.FragmentSearchBinding
import com.amirez.pexels.model.PhotosData
import com.amirez.pexels.presenter.MainViewModel
import com.amirez.pexels.ui.explore.PhotoAdapter
import com.amirez.pexels.utils.isSearchKeyValid
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class SearchFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()
    @Inject
    lateinit var adapter: PhotoAdapter

    companion object {
        private var currentPage = 1
        private var isShowButtonClicked = false
    }

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
        observeOnLiveData()

        binding.etSearch.addTextChangedListener {
            currentPage = 1
            isShowButtonClicked = false
            getPhotos(it.toString())
        }

        binding.btnLoadMore.setOnClickListener {
            currentPage++
            isShowButtonClicked = true
            getPhotos(binding.etSearch.text.toString())
        }

    }

    private fun getPhotos(searchKey: String) {
        if(isSearchKeyValid()) {
            viewModel.getPhotosBySearchKey(searchKey, currentPage)
        }
        else {
            adapter.clearAllData()
            binding.btnLoadMore.visibility = View.GONE
        }
    }

    private fun initRecyclerView() {
        binding.rvSearched.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.rvSearched.adapter = adapter
    }

    private fun observeOnLiveData() {
        viewModel.searchedPhotosLiveData.observe(requireActivity()) {
            if(it.photos.isNotEmpty()) {
                showPhotos(it.photos)
            }
        }

        viewModel.errorsLiveData.observe(requireActivity()) {

        }
    }

    private fun showPhotos(photos: List<PhotosData.Photo>) {
        if(isShowButtonClicked) {
            adapter.setData(photos)
        } else {
            adapter.clearAndSetNewData(photos)
            binding.btnLoadMore.visibility = View.VISIBLE
        }

    }
}