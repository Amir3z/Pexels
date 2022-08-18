package com.amirez.pexels.ui.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.amirez.pexels.databinding.FragmentExploreBinding
import com.amirez.pexels.model.dataclass.CollectionsData
import com.amirez.pexels.model.dataclass.PhotosData
import com.amirez.pexels.model.dataclass.SafeArgsPhoto
import com.amirez.pexels.presenter.ExploreViewModel
import com.amirez.pexels.utils.collectionsData
import com.bumptech.glide.RequestManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ExploreFragment : Fragment(), PhotoAdapter.ExploreClickEvents {

    private lateinit var binding: FragmentExploreBinding
    private val viewModel: ExploreViewModel by viewModels()

    @Inject
    lateinit var glideInstance: RequestManager

    private lateinit var adapter: PhotoAdapter
    private lateinit var viewPagerAdapter: CollectionViewPagerAdapter

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
        observeLiveData()

        binding.btnLoadMorePhotos.setOnClickListener {
            viewModel.getNextPagePhotos()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.saveAllOpenPagesInLiveData()
    }

    private fun initViewPager() {
        viewPagerAdapter = CollectionViewPagerAdapter(glideInstance, this)
        viewPagerAdapter.setData(collectionsData)
        val viewPager = binding.layoutViewpager.vpCollections
        viewPager.adapter = viewPagerAdapter
        viewPager.offscreenPageLimit = 10
    }

    private fun initRecyclerView() {
        adapter = PhotoAdapter(glideInstance, this)
        binding.rvPhotos.layoutManager =
            StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        binding.rvPhotos.adapter = adapter
    }

    private fun observeLiveData() {
        viewModel.photosLiveData.observe(requireActivity()) {
            adapter.setData(it)
            binding.btnLoadMorePhotos.visibility = View.VISIBLE
        }

        viewModel.errorsLiveData.observe(requireActivity()) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }

        viewModel.isConnected.observe(requireActivity()) { isConnected ->
            if(isConnected){
                binding.layoutNoInternet.root.visibility = View.GONE
                initViewPager()
            } else{
                binding.layoutNoInternet.root.visibility = View.VISIBLE
            }
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
            ExploreFragmentDirections.actionExploreFragmentToLargePhotoFragment(data)
        )
    }

    override fun onCollectionClick(collectionsData: CollectionsData) {
        val id = collectionsData.id
        findNavController().navigate(
            ExploreFragmentDirections.actionExploreFragmentToCollectionFragment(id)
        )
    }
}