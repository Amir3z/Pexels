package com.amirez.pexels.feature.collection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.amirez.pexels.R
import com.amirez.pexels.databinding.FragmentCollectionBinding
import com.amirez.pexels.model.Collection
import com.amirez.pexels.model.SafeArgsPhoto
import com.amirez.pexels.utils.*
import com.bumptech.glide.RequestManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CollectionFragment : Fragment(), CollectionAdapter.CollectionsClickEvents {
    private lateinit var binding: FragmentCollectionBinding
    private val viewModel: CollectionViewModel by viewModels()
    private lateinit var adapter: CollectionAdapter
    private lateinit var id: String

    @Inject
    lateinit var glideInstance: RequestManager


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
        viewModel.getFirstPagePhotos(id)

        chooseTitle()
        initRecyclerView()
        observeLiveData()

        binding.btnLoadMore.setOnClickListener {
            viewModel.getPhotos(id)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.saveAllOpenPagesInLiveData()
    }

    private fun initRecyclerView() {
        adapter = CollectionAdapter(glideInstance, this)
        binding.rvCollection.layoutManager =
            StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        binding.rvCollection.adapter = adapter
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
                binding.btnLoadMore.visibility = View.VISIBLE
            }
        }

        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.eventFlow.collectLatest { event ->
                when(event) {
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
            viewModel.getPhotos(id)
        }
    }

    override fun onCollectionClick(collections: Collection.Media) {

        val data = SafeArgsPhoto(
            collections.photographer ?: "N/A",
            collections.alt ?: "N/A",
            collections.id.toString(),
            collections.src!!.large2x,
            collections.url,
            collections.avgColor!!
        )

        findNavController().navigate(
            CollectionFragmentDirections.actionCollectionFragmentToPhotoDetailsFragment(data)
        )
    }

    private fun chooseTitle() {
        when (id) {
            CAR_COLLECTION_ID -> {
                glideInstance.load(R.drawable.ic_car)
                    .into(binding.imgCollection)
                binding.tvCollection.text = "Cars"
            }
            SPACE_COLLECTION_ID -> {
                glideInstance.load(R.drawable.ic_space)
                    .into(binding.imgCollection)
                binding.tvCollection.text = "Space"
            }
            ANIMAL_COLLECTION_ID -> {
                glideInstance.load(R.drawable.ic_animal)
                    .into(binding.imgCollection)
                binding.tvCollection.text = "Animals"
            }
            PASTEL_COLLECTION_ID -> {
                glideInstance.load(R.drawable.ic_pastel)
                    .into(binding.imgCollection)
                binding.tvCollection.text = "Pastel Backgrounds"
            }
            TULIP_COLLECTION_ID -> {
                glideInstance.load(R.drawable.ic_tulip)
                    .into(binding.imgCollection)
                binding.tvCollection.text = "Tulips"
            }
            ART_COLLECTION_ID -> {
                glideInstance.load(R.drawable.ic_art)
                    .into(binding.imgCollection)
                binding.tvCollection.text = "Art"
            }
            TECHNOLOGY_COLLECTION_ID -> {
                glideInstance.load(R.drawable.ic_tech)
                    .into(binding.imgCollection)
                binding.tvCollection.text = "Technology"
            }
            NATURE_COLLECTION_ID -> {
                glideInstance.load(R.drawable.ic_nature)
                    .into(binding.imgCollection)
                binding.tvCollection.text = "Just Nature"
            }
            CODING_COLLECTION_ID -> {
                glideInstance.load(R.drawable.ic_coding)
                    .into(binding.imgCollection)
                binding.tvCollection.text = "Coding"
            }
            COFFEE_COLLECTION_ID -> {
                glideInstance.load(R.drawable.ic_coffee)
                    .into(binding.imgCollection)
                binding.tvCollection.text = "Coffee"
            }
        }
    }
}