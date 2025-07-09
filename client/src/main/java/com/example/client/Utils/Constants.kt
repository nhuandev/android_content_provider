package com.example.client.Utils

object Constants {
    const val KEY_URI_USER = "content://com.example.appcontentprovider.provider/User"
    const val KEY_URI_PRODUCT = "content://com.example.appcontentprovider.provider/Product"

    const val KEY_USER_ID = "id"
    const val KEY_USER_NAME = "userName"
    const val KEY_USER_AGE = "userAge"
    const val KEY_USER_AVATAR = "avatar"
    const val KEY_USER_CITY_NAME = "cityName"

    const val KEY_PRODUCT_ID = "productId"
    const val KEY_PRODUCT_NAME = "productName"
    const val KEY_PRODUCT_PRICE = "productPrice"
    const val KEY_PRODUCT_DESC = "productDesc"
    const val KEY_PRODUCT_IMAGE = "productImage"

    const val STATUS_FAIL = -1
    const val ADD_USER_SUCCESS = 1
    const val DELETE_USER_SUCCESS = 2
    const val UPDATE_USER_SUCCESS = 3

    const val ADD_PRODUCT_SUCCESS = 4
    const val DELETE_PRODUCT_SUCCESS = 5
    const val UPDATE_PRODUCT_SUCCESS = 6
}
