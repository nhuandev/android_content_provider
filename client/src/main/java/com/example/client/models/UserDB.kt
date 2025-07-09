package com.example.client.models

import java.io.Serializable

class UserDB(
    var id: Int = 0,
    var avatar: ByteArray? = null,
    var userName: String = "",
    var userAge: Int = 0,
    var cityName: String = ""
) : Serializable {
}