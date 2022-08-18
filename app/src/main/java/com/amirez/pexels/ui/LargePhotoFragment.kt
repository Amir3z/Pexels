package com.amirez.pexels.ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.amirez.pexels.databinding.FragmentLargePhotoBinding
import com.amirez.pexels.model.dataclass.SafeArgsPhoto
import com.bumptech.glide.RequestManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LargePhotoFragment: Fragment() {
    private lateinit var binding: FragmentLargePhotoBinding
    private lateinit var photo: SafeArgsPhoto
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
        binding.tvAlt.text = photo.alt
        binding.tvName.text = photo.photographer

        val colorHex = photo.avgColor
        val color = Color.parseColor(colorHex)
        binding.tvHexCode.text = colorHex
        binding.tvHexCode.setTextColor(color)
        binding.imgAvgColor.setBackgroundColor(color)

        glide
            .load(photo.src)
            .into(binding.imgLarge)
    }
}