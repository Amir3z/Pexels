package com.amirez.pexels.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.amirez.pexels.databinding.FragmentPhotoDetailsBinding
import com.amirez.pexels.data.SafeArgsPhoto
import com.bumptech.glide.RequestManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PhotoDetailsFragment: Fragment() {
    private lateinit var binding: FragmentPhotoDetailsBinding
    private lateinit var photo: SafeArgsPhoto
    @Inject lateinit var glide: RequestManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPhotoDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        photo = PhotoDetailsFragmentArgs.fromBundle(requireArguments()).photo

        initUI()
    }

    private fun initUI() {
        binding.tvAlt.text = photo.alt
        binding.tvName.text = photo.photographer

        glide
            .load(photo.src)
            .into(binding.imgLarge)
    }
}