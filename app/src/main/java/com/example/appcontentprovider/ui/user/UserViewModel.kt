package com.example.appcontentprovider.ui.user

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.appcontentprovider.utils.ADD_USER_SUCCESS
import com.example.appcontentprovider.utils.DELETE_USER_SUCCESS
import com.example.appcontentprovider.utils.UPDATE_USER_SUCCESS
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val userRepository = UserRepository(application)

    val getAllUser = userRepository.getAllUsers()

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    init {
        fetchUsers()
    }

    fun fetchUsers() {
        _loading.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                userRepository.getAllUsers()
            } catch (e: Exception) {
                Log.d("UserViewModel", "fetchUsers: ${e.message}")
            } finally {
                _loading.postValue(false)
            }
        }
    }

    fun insertUser(user: UserDB) {
        _loading.postValue(false)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                userRepository.insertUser(user)
                _message.postValue(ADD_USER_SUCCESS)
            } catch (e: Exception) {
                Log.d("UserViewModel", "insertUser: ${e.message}")
            } finally {
                _loading.postValue(false)
            }
        }
    }

    fun deleteUser(user: UserDB) {
        _loading.postValue(false)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                userRepository.deleteUser(user)
                _message.postValue(DELETE_USER_SUCCESS)
            } catch (e: Exception) {
                Log.d("UserViewModel", "deleteUser: ${e.message}")
            } finally {
                _loading.postValue(false)
            }
        }
    }

    fun updateUser(user: UserDB) {
        _loading.postValue(false)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                userRepository.updateUser(user)
                _message.postValue(UPDATE_USER_SUCCESS)
            } catch (e: Exception) {
                Log.d("UserViewModel", "updateUser: ${e.message}")
            } finally {
                _loading.postValue(false)
            }
        }
    }
}