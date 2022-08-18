package com.amirez.pexels.ui.collection

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.amirez.pexels.R
import com.amirez.pexels.databinding.FragmentCollectionBinding
import com.amirez.pexels.model.dataclass.Collection
import com.amirez.pexels.model.dataclass.SafeArgsPhoto
import com.amirez.pexels.presenter.CollectionViewModel
import com.amirez.pexels.utils.*
import com.bumptech.glide.RequestManager
import dagger.hilt.android.AndroidEntryPoint
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
        viewModel.collectionLiveData.observe(requireActivity()) {
            adapter.setData(it)
            binding.btnLoadMore.visibility = View.VISIBLE
        }
        viewModel.errorsLiveData.observe(requireActivity()) {
            Log.d("tagx", "observeLiveData: $it")
        }
        viewModel.isConnected.observe(requireActivity()) { isConnected ->
            if (isConnected) {
                binding.layoutNoInternet.root.visibility = View.GONE
            } else {
                binding.layoutNoInternet.root.visibility = View.VISIBLE
            }
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
            CollectionFragmentDirections.actionCollectionFragmentToLargePhotoFragment(data)
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