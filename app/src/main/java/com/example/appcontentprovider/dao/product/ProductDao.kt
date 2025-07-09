package com.example.appcontentprovider.dao.product

import android.content.ContentValues
import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProduct(product: ProductDB)

    @Query("SELECT * FROM product ORDER BY productId DESC")
    fun getAllProducts(): LiveData<List<ProductDB>>

    @Query("DELETE FROM product WHERE productId = :id")
    fun deleteProductId(id: Int)

    @Query("DELETE FROM product")
    fun deleteAllProducts()

    @Update
    fun updateProduct(product: ProductDB): Int

    // Provider
    @Insert
    fun insertProductProvider(contents: ContentValues) {
        val product = ProductDB(
            productId = contents.getAsInteger("productId"),
            productName = contents.getAsString("productName"),
            productPrice = contents.getAsDouble("productPrice"),
            productDesc = contents.getAsString("productDesc"),
            productImage = contents.getAsByteArray("productImage"),
        )
        insertProduct(product)
    }

    @Query("SELECT * FROM product")
    fun getAllProductsProvider(): Cursor

    @Query("DELETE FROM product WHERE productId = :id")
    fun deleteProductIdProvider(id: Int): Int

    @Update
    fun updateProductProvider(contents: ContentValues) : Int {
        val product = ProductDB(
            productId = contents.getAsInteger("productId"),
            productName = contents.getAsString("productName"),
            productPrice = contents.getAsDouble("productPrice"),
            productDesc = contents.getAsString("productDescription"),
            productImage = contents.getAsByteArray("productImage"),
        )
        return updateProduct(product)
    }
}
