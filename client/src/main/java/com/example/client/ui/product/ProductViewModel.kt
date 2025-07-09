package com.example.client.ui.product

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.client.Utils.Constants
import com.example.client.models.ProductDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductViewModel(application: Application) : AndroidViewModel(application) {
    private val productRepository = ProductRepository(application)

    private val _products = MutableLiveData<List<ProductDB>>()
    val products: LiveData<List<ProductDB>> = _products

    private val _message = MutableLiveData<Int>()
    val message: LiveData<Int> = _message

    init {
        loadProducts()
    }

    fun loadProducts() {
        viewModelScope.launch {
            try {
                val products = productRepository.getAllProducts()
                _products.postValue(products)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun insertProduct(product: ProductDB, callBack: (Boolean) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = productRepository.insertProduct(product)
                callBack(result)
                _message.postValue(Constants.ADD_PRODUCT_SUCCESS)
            } catch (e: Exception) {
                _message.postValue(Constants.STATUS_FAIL)
                e.printStackTrace()
            }
        }
    }
}
