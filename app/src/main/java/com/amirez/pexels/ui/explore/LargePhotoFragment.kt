package com.amirez.pexels.ui.explore

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.amirez.pexels.databinding.FragmentLargePhotoBinding
import com.amirez.pexels.model.PhotosData
import com.bumptech.glide.RequestManager
import javax.inject.Inject

class LargePhotoFragment: Fragment() {
    private lateinit var binding: FragmentLargePhotoBinding
    private lateinit var photo: PhotosData.Photo
    @Inject lateinit var glide: RequestManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLargePhotoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        photo = LargePhotoFragmentArgs.fromBundle(requireArguments()).photo

        initUI()
    }

    private fun initUI() {
        binding.tvAltLarge.text = photo.alt
        binding.tvPhotographerNameLarge.text = photo.photographer

        val colorHex = photo.avgColor
        val color = Color.parseColor(colorHex)
        binding.tvHexCodeLarge.text = colorHex
        binding.tvHexCodeLarge.setTextColor(color)
        binding.imgAvgColorLarge.setBackgroundColor(color)

        glide
            .load(photo.src.large)
            .into(binding.imgLarge)
    }
}