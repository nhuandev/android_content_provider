package com.example.client.ui.product

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.client.R
import com.example.client.Utils.ConvertData
import com.example.client.databinding.ItemProductBinding
import com.example.client.models.ProductDB

class ProductAdapter(
    private var products: List<ProductDB>,
    private val onItemClicked: (ProductDB) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newProducts: List<ProductDB>) {
        products = newProducts
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: ProductDB) {
            binding.apply {
                tvProductName.text = product.productName
                tvProductPrice.text = product.productPrice.toString()
                tvProductDesc.text = product.productDesc
                product.productImage?.let {
                    imgProduct.setImageBitmap(ConvertData(root.context).byteArrayToBitmap(product.productImage!!))
                } ?: run {
                    imgProduct.setImageResource(R.drawable.ic_launcher_foreground)
                }

                root.setOnClickListener {
                    onItemClicked(product)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val product = products[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int {
        return products.size
    }
}
