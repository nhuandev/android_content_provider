package com.example.client.ContentResolver

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import androidx.core.net.toUri
import com.example.client.Utils.Constants
import com.example.client.models.UserDB

class ContentResolverUser(private val context: Context) {
    private val contentResolver = context.contentResolver
    private val uriUser = Constants.KEY_URI_USER.toUri()

    @SuppressLint("Recycle")
    fun getUsers(): List<UserDB> {
        val users = mutableListOf<UserDB>()
        val cursor = contentResolver.query(uriUser, null, null, null, null)
        cursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(Constants.KEY_USER_ID))
                val userName = getString(getColumnIndexOrThrow(Constants.KEY_USER_NAME))
                val userAge = getInt(getColumnIndexOrThrow(Constants.KEY_USER_AGE))
                val avatar = getBlob(getColumnIndexOrThrow(Constants.KEY_USER_AVATAR))
                users.add(UserDB(id, avatar, userName, userAge))
            }
        }
        return users
    }

    fun insertUser(user: UserDB): Boolean {
        return try {
            val values = ContentValues().apply {
                put(Constants.KEY_USER_ID, user.id)
                put(Constants.KEY_USER_NAME, user.userName)
                put(Constants.KEY_USER_AGE, user.userAge)
                put(Constants.KEY_USER_AVATAR, user.avatar)
            }
            contentResolver.insert(uriUser, values)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun deleteUser(id: Int): Boolean {
        return try {
            val uri = ContentUris.withAppendedId(uriUser, id.toLong())
            contentResolver.delete(uri, null, null)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun updateUser(user: UserDB): Boolean {
        return try {
            val values = ContentValues().apply {
                put(Constants.KEY_USER_ID, user.id)
                put(Constants.KEY_USER_NAME, user.userName)
                put(Constants.KEY_USER_AGE, user.userAge)
                put(Constants.KEY_USER_AVATAR, user.avatar)
            }
            val selection = "${Constants.KEY_USER_ID} = ?"
            val selectionArgs = arrayOf(user.id.toString())
            contentResolver.update(uriUser, values, selection, selectionArgs)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}
