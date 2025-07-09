package com.example.client.ui.product

import android.content.Context
import com.example.client.ContentResolver.ContentResolverProduct
import com.example.client.models.ProductDB

class ProductRepository(context: Context) {
    private val contentResolverProduct = ContentResolverProduct(context)

    fun getAllProducts(): List<ProductDB> {
        return contentResolverProduct.getProducts()
    }

    fun insertProduct(product: ProductDB): Boolean {
        return contentResolverProduct.insertProduct(product)
    }
}
