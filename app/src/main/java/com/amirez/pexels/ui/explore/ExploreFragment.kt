package com.amirez.pexels.ui.explore

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.amirez.pexels.databinding.FragmentExploreBinding
import com.amirez.pexels.model.PhotosData
import com.amirez.pexels.presenter.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ExploreFragment : Fragment(), PhotoAdapter.PhotoEvent {
    private lateinit var binding: FragmentExploreBinding
    private val viewModel: MainViewModel by activityViewModels()

    @Inject
    lateinit var adapter: PhotoAdapter

    companion object {
        private var currentPage = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentExploreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        observeOnLiveData()

        binding.btnLoadMorePhotos.setOnClickListener {
            viewModel.getPhotosByPage(++currentPage)
        }
    }

    private fun observeOnLiveData() {
        viewModel.photosLiveData.observe(requireActivity()) {
            adapter.setData(it.photos)
            binding.btnLoadMorePhotos.visibility = View.VISIBLE
        }

        viewModel.errorsLiveData.observe(requireActivity()) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            Log.d("tagx", "onViewCreated: $it")
        }
    }

    private fun initRecyclerView() {
        binding.rvPhotos.layoutManager =
            StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        binding.rvPhotos.adapter = adapter
    }

    override fun onPhotoClick(photo: PhotosData.Photo, position:Int) {
        Log.d("tagk", "onPhotoClick: $position")
    }

    override fun onPhotoLongClick(photo: PhotosData.Photo) {

    }
}