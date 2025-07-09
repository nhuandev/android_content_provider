package com.example.client.ui.product

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.client.R
import com.example.client.Utils.Constants
import com.example.client.databinding.ActivityProductAddBinding
import com.example.client.extention.toast
import com.example.client.models.ProductDB

class ProductAddActivity : AppCompatActivity() {
    private val binding by lazy { ActivityProductAddBinding.inflate(layoutInflater) }
    private val productViewModel: ProductViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initUI()
        obServer()
    }

    private fun initUI() {
        binding.apply {
            btnProductAdd.setOnClickListener {
                val productName = tieProductName.text.toString()
                val productPrice = tieProductPrice.text.toString()
                val productDesc = tieProductDesc.text.toString()

                if (productName.isEmpty() || productPrice.isEmpty() || productDesc.isEmpty()) {
                    toast(R.string.lb_toast_check_value)
                    return@setOnClickListener
                }
                productViewModel.insertProduct(
                    ProductDB(
                        productName = productName,
                        productPrice = productPrice.toDouble(),
                        productDesc = productDesc,
                        productImage = null
                    ),
                    callBack = {
                        finish()
                    }
                )
            }
        }
    }

    private fun obServer() {
        productViewModel.message.observe(this@ProductAddActivity) {
            when (it) {
                Constants.ADD_PRODUCT_SUCCESS -> {
                    toast(R.string.add_user_success, Toast.LENGTH_SHORT)
                }

                else -> {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
