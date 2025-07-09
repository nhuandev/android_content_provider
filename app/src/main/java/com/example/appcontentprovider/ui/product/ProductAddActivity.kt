package com.example.appcontentprovider.ui.product

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.appcontentprovider.dao.product.ProductDB
import com.example.appcontentprovider.databinding.ActivityProductAddBinding
import com.example.appcontentprovider.utils.INTENT_PRODUCT

class ProductAddActivity : AppCompatActivity() {
    private val binding by lazy { ActivityProductAddBinding.inflate(layoutInflater) }
    private val productViewModel: ProductViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initUI()
        onEvent()
    }

    private fun initUI() {
        binding.apply {
            val intentProduct = intent.getSerializableExtra(INTENT_PRODUCT) as? ProductDB
            intentProduct?.let {
                tieProductName.setText(it.productName)
                tieProductPrice.setText(it.productPrice.toString())
                tieProductDesc.setText(it.productDesc)
                btnProductUpdate.isEnabled = true
                btnProductAdd.isEnabled = false
            } ?: run {
                btnProductUpdate.isEnabled = false
                btnProductAdd.isEnabled = true
            }

            supportActionBar?.title = "Product Add/Edit"
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun onEvent() {
        binding.apply {
            btnProductAdd.setOnClickListener {
                productViewModel.insertProduct(
                    ProductDB(
                        productName = tieProductName.text.toString(),
                        productPrice = tieProductPrice.text.toString().toDouble(),
                        productDesc = tieProductDesc.text.toString(),
                        productImage = null
                    )
                )
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}

