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
import com.amirez.pexels.model.CollectionsData
import com.amirez.pexels.model.PhotosData
import com.amirez.pexels.presenter.MainViewModel
import com.amirez.pexels.utils.NetworkChecker
import com.amirez.pexels.utils.collectionsData
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ExploreFragment : Fragment(), ExploreClickEvents {

    private lateinit var binding: FragmentExploreBinding
    private val viewModel: MainViewModel by activityViewModels()
    @Inject
    lateinit var adapter: PhotoAdapter
    @Inject
    lateinit var viewPagerAdapter: CollectionAdapter
    @Inject
    lateinit var networkChecker: NetworkChecker

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

        if (networkChecker.isConnected) {
            viewModel.getPhotosByPage(currentPage)
            binding.layoutNoInternet.root.visibility = View.GONE
            initViewPager()
        } else {
            binding.layoutNoInternet.root.visibility = View.VISIBLE
        }

        initRecyclerView()
        observeOnLiveData()

        binding.btnLoadMorePhotos.setOnClickListener {
            viewModel.getPhotosByPage(++currentPage)
        }
    }

    private fun initViewPager() {
        viewPagerAdapter.setData(collectionsData)
        val viewPager = binding.layoutViewpager.vpCollections
        viewPager.adapter = viewPagerAdapter
        viewPager.offscreenPageLimit = 10

//        val circleIndicator = binding.layoutViewpager.circleIndicator3
//        circleIndicator.setViewPager(viewPager)
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

    override fun onPhotoClick(photo: PhotosData.Photo, position: Int) {
        Log.d("tagk", "onPhotoClick: $position")
    }

    override fun onPhotoLongClick(photo: PhotosData.Photo) {

    }

    override fun onCollectionClick(collectionsData: CollectionsData) {
        Log.d("tagx", "onCollectionClick: ${collectionsData.title}")
    }
}