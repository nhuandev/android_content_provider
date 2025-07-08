package com.example.client.ui.user

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.client.ContentResolver.ContentResolverUser
import com.example.client.Utils.Constants
import com.example.client.models.UserDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val contentResolverUser = ContentResolverUser(application.applicationContext)

    private val _users = MutableLiveData<List<UserDB>>()
    val users: LiveData<List<UserDB>> = _users

    private val _message = MutableLiveData<Int>()
    val message: LiveData<Int> = _message

    fun loadUsers() {
        viewModelScope.launch {
            try {
                val users = contentResolverUser.getUsers()
                _users.postValue(users)
            } catch (e: Exception) {

                e.printStackTrace()
            } finally {

            }
        }
    }

    fun insertUser(user: UserDB, callBack: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val result = contentResolverUser.insertUser(user)
                callBack(result)
                _message.postValue(Constants.ADD_USER_SUCCESS)
            } catch (e: Exception) {
                _message.postValue(Constants.STATUS_FAIL)
                e.printStackTrace()
            } finally {

            }
        }
    }

    fun deleteUser(id: Int) {
        viewModelScope.launch {
            try {
                contentResolverUser.deleteUser(id)
                _message.postValue(Constants.DELETE_USER_SUCCESS)
            } catch (e: Exception) {
                _message.postValue(Constants.STATUS_FAIL)
                e.printStackTrace()
            }
        }
    }

    fun updateUser(user: UserDB) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = contentResolverUser.updateUser(user)
                if (result) {
                    _message.postValue(Constants.UPDATE_USER_SUCCESS)
                }
            } catch (e: Exception) {
                _message.postValue(Constants.STATUS_FAIL)
                e.printStackTrace()
            }
        }
    }
}
