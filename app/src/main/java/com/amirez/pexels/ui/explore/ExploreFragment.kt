package com.amirez.pexels.ui.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.amirez.pexels.databinding.FragmentExploreBinding
import com.amirez.pexels.data.CollectionsData
import com.amirez.pexels.data.PhotosData
import com.amirez.pexels.data.SafeArgsPhoto
import com.amirez.pexels.utils.UIEvent
import com.amirez.pexels.utils.collectionsData
import com.bumptech.glide.RequestManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
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
        initViewPager()
        observeLiveData()

        binding.btnLoadMorePhotos.setOnClickListener {
            viewModel.getPhotos()
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

        val indicator = binding.layoutViewpager.indicator
        indicator.attachToPager(viewPager)
    }

    private fun initRecyclerView() {
        adapter = PhotoAdapter(glideInstance, this)
        adapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.ALLOW
        binding.rvPhotos.layoutManager =
            StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        binding.rvPhotos.adapter = adapter
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
                adapter.setData(state.data)
                binding.btnLoadMorePhotos.visibility = View.VISIBLE
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
            viewModel.getPhotos()
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
            ExploreFragmentDirections.actionExploreFragmentToPhotoDetailsFragment(data)
        )
    }

    override fun onCollectionClick(collectionsData: CollectionsData) {
        val id = collectionsData.id
        findNavController().navigate(
            ExploreFragmentDirections.actionExploreFragmentToCollectionFragment(id)
        )
    }
}