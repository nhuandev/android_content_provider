package com.example.picker

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.picker.databinding.ItemGalleryBinding

class GalleryAdapter(
    private val images: List<Uri>,
    private val onItemClick: (Uri) -> Unit
) : RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ItemGalleryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(image: Uri) {
            binding.apply {
                imgGallery.setImageURI(image)
                root.setOnClickListener { onItemClick(image) }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding =
            ItemGalleryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val image = images[position]
        holder.bind(image)
    }

    override fun getItemCount(): Int {
        return images.size
    }
}
