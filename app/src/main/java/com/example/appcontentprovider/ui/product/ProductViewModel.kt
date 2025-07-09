package com.example.appcontentprovider.ui.product

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.appcontentprovider.dao.product.ProductDB
import com.example.appcontentprovider.utils.ADD_PRODUCT_SUCCESS
import com.example.appcontentprovider.utils.FAILED_ADD_PRODUCT
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductViewModel(application: Application) : AndroidViewModel(application) {
    private val productRepository = ProductRepository(application)

    private val _message = MutableLiveData<Int>()
    val message: MutableLiveData<Int> = _message

    val getAllProducts = productRepository.getAllProducts()

    fun insertProduct(product: ProductDB) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                productRepository.insertProduct(product)
                _message.postValue(ADD_PRODUCT_SUCCESS)
            } catch (e: Exception) {
                e.printStackTrace()
                _message.postValue(FAILED_ADD_PRODUCT)
            }
        }
    }
}
