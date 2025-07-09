package com.example.appcontentprovider.dao.product

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "product")
class ProductDB(
    @PrimaryKey(autoGenerate = true)
    var productId: Int = 0,
    var productName: String = "",
    var productPrice: Double = 0.0,
    var productDesc: String = "",
    var productImage: ByteArray? = null
) : Serializable {
}
