package com.example.appcontentprovider.ui.user

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.appcontentprovider.dao.AppDatabase
import com.example.appcontentprovider.provider.user.UserDao

class UserRepository(context: Context) {
    private val userDao: UserDao

    init {
        val database = AppDatabase.Companion.getDatabase(context)
        userDao = database.userDao()
    }

    fun insertUser(user: UserDB) {
        userDao.insertUser(user)
    }

    fun getAllUsers(): LiveData<List<UserDB>> {
        return userDao.getAllUsers()
    }

    fun deleteUser(user: UserDB) {
        userDao.deleteUser(user)
    }

    fun updateUser(user: UserDB) {
        userDao.updateUser(user)
    }
}
