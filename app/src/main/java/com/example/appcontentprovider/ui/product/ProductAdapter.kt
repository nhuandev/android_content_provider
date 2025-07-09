package com.example.appcontentprovider.ui.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appcontentprovider.R
import com.example.appcontentprovider.dao.product.ProductDB
import com.example.appcontentprovider.databinding.ItemProductBinding
import com.example.appcontentprovider.utils.ConvertData

class ProductAdapter(
    private var listProduct: List<ProductDB>,
    private val onItemClick: (ProductDB) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

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
                    onItemClick(product)
                }
            }
        }
    }

    fun updateData(newList: List<ProductDB>) {
        listProduct = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = listProduct[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int {
        return listProduct.size
    }
}
