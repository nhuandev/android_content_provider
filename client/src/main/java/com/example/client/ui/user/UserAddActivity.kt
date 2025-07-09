package com.example.client.ui.user

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.client.R
import com.example.client.Utils.Constants
import com.example.client.Utils.ConvertData
import com.example.client.databinding.ActivityUserAddBinding
import com.example.client.extention.toast
import com.example.client.models.UserDB

class UserAddActivity : AppCompatActivity() {
    private val binding by lazy { ActivityUserAddBinding.inflate(layoutInflater) }
    private val userViewModel: UserViewModel by viewModels()
    private lateinit var userDB: UserDB
    private var selectedImage: ByteArray? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initUI()
        observeViewModel()
    }

    private fun observeViewModel() {
        userViewModel.message.observe(this@UserAddActivity) {
            when (it) {
                Constants.ADD_USER_SUCCESS -> {
                    toast(R.string.add_user_success)
                    finish()
                }

                Constants.DELETE_USER_SUCCESS -> {
                    toast(R.string.delete_user_success)
                    finish()
                }

                Constants.UPDATE_USER_SUCCESS -> {
                    toast(R.string.update_user_success)
                    finish()
                }

                Constants.STATUS_FAIL -> {
                    toast(R.string.status_fail)
                }
            }
        }
    }

    private fun initUI() {
        val intentCheck = intent.getSerializableExtra("KEY_CHECK_FEATURE")
        binding.apply {
            imgUser.setOnClickListener {
                pickImageLauncher.launch(arrayOf("image/*"))
            }

            if (intentCheck == 0) {
                btnAddUser.isEnabled = true
                btnUpdateUser.isEnabled = false
            } else {
                btnUpdateUser.isEnabled = true
                btnAddUser.isEnabled = false
                (intent.getSerializableExtra("KEY_USER_ID") as? UserDB)?.let {
                    userDB = it
                    etUserName.setText(it.userName)
                    etUserAge.setText(it.userAge.toString())

                    it.avatar?.let { avatarBytes ->
                        val imageUser =
                            BitmapFactory.decodeByteArray(avatarBytes, 0, avatarBytes.size)
                        imgUser.setImageBitmap(imageUser)
                    }
                } ?: run {
                    finish()
                    return
                }
            }

            btnUpdateUser.setOnClickListener {
                userViewModel.updateUser(
                    UserDB(
                        id = userDB.id,
                        userName = etUserName.text.toString(),
                        userAge = etUserAge.text.toString().toInt(),
                        avatar = selectedImage ?: userDB.avatar
                    )
                )
            }

            btnDeleteUser.setOnClickListener {
                AlertDialog.Builder(this@UserAddActivity)
                    .setTitle(R.string.dialog_user)
                    .setMessage(R.string.dialog_user)
                    .setPositiveButton(R.string.lb_yes) { _, _ ->
                        userViewModel.deleteUser(
                            id = userDB.id
                        )
                    }
                    .setNegativeButton(R.string.lb_no, null)
                    .show()
            }

            btnAddUser.setOnClickListener {
                val name = etUserName.text.toString()
                val age = etUserAge.text.toString()
                val avatar = selectedImage
                userViewModel.insertUser(
                    UserDB(
                        userName = name,
                        userAge = age.toInt(),
                        avatar = avatar
                    ),
                    callBack = {
                        finish()
                    }
                )
            }
        }
    }

    private fun handelSaveImage(uri: Uri?) {
        uri?.let {
            val originalBytes = ConvertData(this@UserAddActivity).uriToByteArray(it)
            originalBytes?.let {
                val originalBitmap =
                    BitmapFactory.decodeByteArray(originalBytes, 0, originalBytes.size)

                val resizedBitmap = ConvertData(this@UserAddActivity).resizeBitmap(originalBitmap)
                selectedImage = ConvertData(this@UserAddActivity).bitmapToByteArray(resizedBitmap)

                binding.imgUser.setImageBitmap(resizedBitmap)
            } ?: run {
                Log.d("TAG", "handelSaveImage: error")
            }
        }
    }

    private val pickImageLauncher =
        registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri: Uri? ->
            uri?.let {
                handelSaveImage(uri)
            }
        }
}
