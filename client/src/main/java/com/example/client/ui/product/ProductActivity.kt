package com.example.client.ui.product

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.client.databinding.ActivityProductBinding

class ProductActivity : AppCompatActivity() {
    private val binding by lazy { ActivityProductBinding.inflate(layoutInflater) }
    private val productViewModel: ProductViewModel by viewModels()
    private lateinit var adapterProduct: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initViews()
        obServer()
    }

    override fun onResume() {
        super.onResume()
        productViewModel.loadProducts()
    }

    private fun initViews() {
        binding.apply {
            adapterProduct = ProductAdapter(emptyList(), {

            })
            recProduct.layoutManager = LinearLayoutManager(this@ProductActivity)
            recProduct.adapter = adapterProduct

            btnAddProduct.setOnClickListener {
                val intent = Intent(this@ProductActivity, ProductAddActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun obServer() {
        productViewModel.products.observe(this@ProductActivity) {
            adapterProduct.updateData(it)
        }
    }
}
