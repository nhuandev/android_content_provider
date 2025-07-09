package com.example.appcontentprovider.ui.user

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "user")
class UserDB(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var avatar: ByteArray? = null,
    var userName: String = "",
    var userAge: Int = 0,
    var cityName: String = ""
): Serializable {
}