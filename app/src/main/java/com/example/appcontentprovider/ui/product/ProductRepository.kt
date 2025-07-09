package com.example.appcontentprovider.ui.product

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.appcontentprovider.dao.AppDatabase
import com.example.appcontentprovider.dao.product.ProductDB
import com.example.appcontentprovider.dao.product.ProductDao

class ProductRepository(context: Context) {
    private val productDao: ProductDao

    init {
        val database = AppDatabase.getDatabase(context)
        productDao = database.productDao()
    }

    fun insertProduct(product: ProductDB) {
        productDao.insertProduct(product)
    }

    fun getAllProducts(): LiveData<List<ProductDB>> {
        return productDao.getAllProducts()
    }
}
