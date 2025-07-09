package com.example.appcontentprovider.ui.product

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.appcontentprovider.databinding.ActivityProductBinding
import com.example.appcontentprovider.utils.INTENT_PRODUCT
import com.example.appcontentprovider.utils.KEY_CHECK

class ProductActivity : AppCompatActivity() {
    private val binding by lazy { ActivityProductBinding.inflate(layoutInflater) }
    private val productViewModel: ProductViewModel by viewModels()
    private lateinit var adapterProduct: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.title = "Product"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initUI()
        onEvent()
        observeViewModel()
    }

    private fun onEvent() {
        binding.apply {
            btnAddProduct.setOnClickListener {
                val intentProduct = Intent(this@ProductActivity, ProductAddActivity::class.java)
                intentProduct.putExtra(KEY_CHECK, false) // False is add
                startActivity(intentProduct)
            }
        }
    }

    private fun initUI() {

        binding.apply {
            adapterProduct = ProductAdapter(emptyList(), onItemClick = {
                val intentProduct = Intent(this@ProductActivity, ProductAddActivity::class.java)
                intentProduct.putExtra(KEY_CHECK, true) // True is edit
                intentProduct.putExtra(INTENT_PRODUCT, it)
                startActivity(intentProduct)
            })
            recProduct.layoutManager = GridLayoutManager(this@ProductActivity, 2)
            recProduct.adapter = adapterProduct
        }
    }

    private fun observeViewModel() {
        productViewModel.getAllProducts.observe(this) {
            adapterProduct.updateData(it)
        }

        productViewModel.message.observe(this) {
            when (it) {
                1 -> {
                    Toast.makeText(this, "Add product success", Toast.LENGTH_SHORT).show()
                }

                2 -> {
                    Toast.makeText(this, "Add product failed", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        Log.d("ProductActivity", "onSupportNavigateUp: ")
        return true
    }
}
