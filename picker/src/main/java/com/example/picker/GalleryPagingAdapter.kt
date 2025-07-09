package com.example.picker

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.airbnb.lottie.LottieCompositionFactory
import com.airbnb.lottie.LottieDrawable
import com.bumptech.glide.Glide
import com.example.picker.databinding.ItemGalleryBinding

class GalleryPagingAdapter :
    PagingDataAdapter<Uri, GalleryPagingAdapter.GalleryViewHolder>(UriComparator) {

    object UriComparator : DiffUtil.ItemCallback<Uri>() {
        override fun areItemsTheSame(oldItem: Uri, newItem: Uri) = oldItem == newItem
        override fun areContentsTheSame(oldItem: Uri, newItem: Uri) = true
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GalleryViewHolder {
        val binding = ItemGalleryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GalleryViewHolder(binding, parent.context)
    }

    inner class GalleryViewHolder(
        private val binding: ItemGalleryBinding,
        private val context: Context
    ) : ViewHolder(binding.root) {

        fun bind(uri: Uri) {
            val lottieDrawable = LottieDrawable()
            LottieCompositionFactory.fromRawRes(context, R.raw.animation_load)
                .addListener { composition ->
                    lottieDrawable.composition = composition
                    lottieDrawable.repeatCount = LottieDrawable.INFINITE
                    lottieDrawable.playAnimation()

                    Glide.with(context)
                        .load(uri)
                        .placeholder(lottieDrawable)
                        .error(R.drawable.ic_launcher_foreground)
                        .into(binding.imgGallery)
                }
                .addFailureListener {
                    Glide.with(context)
                        .load(uri)
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .error(R.drawable.ic_launcher_foreground)
                        .into(binding.imgGallery)
                }
        }
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }
}
