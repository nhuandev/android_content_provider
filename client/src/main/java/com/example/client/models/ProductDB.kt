package com.example.client.models

import java.io.Serializable

class ProductDB(
    var productId: Int = 0,
    var productName: String = "",
    var productPrice: Double = 0.0,
    var productDesc: String = "",
    var productImage: ByteArray? = null
) : Serializable {
}
