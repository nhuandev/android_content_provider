package com.example.picker

import android.os.Bundle
import android.util.Log
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.picker.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var galleryPagingAdapter: GalleryPagingAdapter
    private val viewModel by viewModels<GalleryViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        binding.apply {
            galleryPagingAdapter = GalleryPagingAdapter()
            recGallery.layoutManager = GridLayoutManager(this@MainActivity, 4)
            recGallery.adapter = galleryPagingAdapter
            lifecycleScope.launch {
                viewModel.pagedImages.collectLatest {
                    galleryPagingAdapter.submitData(it)
                }
            }

            btnPicker.setOnClickListener {
                pickerPhoto.launch(
                    PickVisualMediaRequest(PickVisualMedia.ImageOnly)
                )
            }
        }
    }

    private val pickerPhoto =
        registerForActivityResult(PickVisualMedia()) { uri ->
            if (uri != null) {
                binding.imgPicker.setImageURI(uri)
            } else {
                Log.d("PhotoPicker", "No media selected")
            }
        }
}
