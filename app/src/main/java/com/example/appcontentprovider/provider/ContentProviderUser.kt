package com.example.appcontentprovider.provider

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import androidx.core.net.toUri
import com.example.appcontentprovider.dao.AppDatabase
import com.example.appcontentprovider.utils.KEY_PROVIDER_AUTHORITY
import com.example.appcontentprovider.utils.KEY_PROVIDER_UER

class ContentProviderUser : ContentProvider() {
    companion object {
        private const val AUTHORITY = KEY_PROVIDER_AUTHORITY
        private const val USER_TABLE = KEY_PROVIDER_UER
        val CONTENT_URI_USER: Uri = "content://$AUTHORITY/$USER_TABLE".toUri()

        private const val USER = 1
        private const val USER_ID = 2

        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI(AUTHORITY, USER_TABLE, USER)
            addURI(AUTHORITY, "$USER_TABLE/#", USER_ID)
        }
    }

    private lateinit var appDatabase: AppDatabase

    override fun onCreate(): Boolean {
        appDatabase = AppDatabase.getDatabase(context!!)
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? {
        return when (sUriMatcher.match(uri)) {
            USER -> {
                val cursor = appDatabase.userDao().getAllUsersProvider()
                cursor.setNotificationUri(context!!.contentResolver, CONTENT_URI_USER)
                cursor
            }

            else -> {
                throw IllegalArgumentException("Unknown URI: $uri")
            }
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return when (sUriMatcher.match(uri)) {
            USER -> {
                val id = appDatabase.userDao().insertUserProvider(values!!)
                context!!.contentResolver.notifyChange(CONTENT_URI_USER, null)
                "$CONTENT_URI_USER/$id".toUri()
            }

            else -> {
                throw IllegalArgumentException("Unknown URI: $uri")
            }
        }
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return when (sUriMatcher.match(uri)) {
            USER_ID -> {
                val id = ContentUris.parseId(uri)
                val count = appDatabase.userDao().deleteUserIdProvider(id.toInt())
                context!!.contentResolver.notifyChange(CONTENT_URI_USER, null)
                count
            }

            else -> {
                throw IllegalArgumentException("Unknown URI: $uri")
            }
        }
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        return when (sUriMatcher.match(uri)) {
            USER -> {
                val count = appDatabase.userDao().updateUserProvider(values!!)
                context!!.contentResolver.notifyChange(CONTENT_URI_USER, null)
                count
            }

            else -> {
                return 0
            }
        }
    }

    override fun getType(uri: Uri): String? {
        return null
    }
}
