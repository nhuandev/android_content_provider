package com.example.client.ContentResolver

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import androidx.core.net.toUri
import com.example.client.Utils.Constants
import com.example.client.Utils.Constants.KEY_URI_PRODUCT
import com.example.client.models.ProductDB

class ContentResolverProduct(private val context: Context) {
    private val contentResolver = context.contentResolver
    private val uriProduct = KEY_URI_PRODUCT.toUri()

    @SuppressLint("Recycle")
    fun getProducts(): List<ProductDB> {
        val products = mutableListOf<ProductDB>()
        val cursor = contentResolver.query(uriProduct, null, null, null, null)
        cursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(Constants.KEY_PRODUCT_ID))
                val productName = getString(getColumnIndexOrThrow(Constants.KEY_PRODUCT_NAME))
                val productPrice = getDouble(getColumnIndexOrThrow(Constants.KEY_PRODUCT_PRICE))
                val productDesc = getString(getColumnIndexOrThrow(Constants.KEY_PRODUCT_DESC))
                val productImage = getBlob(getColumnIndexOrThrow(Constants.KEY_PRODUCT_IMAGE))
                products.add(ProductDB(id, productName, productPrice, productDesc, productImage))
            }
        }
        return products
    }

    fun insertProduct(product: ProductDB): Boolean {
        return try {
            val values = ContentValues().apply {
                put(Constants.KEY_PRODUCT_ID, product.productId)
                put(Constants.KEY_PRODUCT_NAME, product.productName)
                put(Constants.KEY_PRODUCT_PRICE, product.productPrice)
                put(Constants.KEY_PRODUCT_DESC, product.productDesc)
                put(Constants.KEY_PRODUCT_IMAGE, product.productImage)
            }
            contentResolver.insert(uriProduct, values)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}
