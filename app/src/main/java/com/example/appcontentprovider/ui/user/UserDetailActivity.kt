package com.example.appcontentprovider.ui.user

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.appcontentprovider.R
import com.example.appcontentprovider.databinding.ActivityUserDetailBinding
import com.example.appcontentprovider.extention.toast
import com.example.appcontentprovider.utils.ConvertData
import com.example.appcontentprovider.utils.DELETE_USER_SUCCESS
import com.example.appcontentprovider.utils.INTENT_USER
import com.example.appcontentprovider.utils.UPDATE_USER_SUCCESS

class UserDetailActivity : AppCompatActivity() {
    private val binding by lazy { ActivityUserDetailBinding.inflate(layoutInflater) }
    private val userViewModel: UserViewModel by viewModels()

    private var selectedImage: ByteArray? = null
    private lateinit var user: UserDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initUI()
        observeViewModel()
    }

    private fun initUI() {
        user = intent.getSerializableExtra(INTENT_USER) as UserDB
        binding.apply {
            setSupportActionBar(toolbar)
            supportActionBar?.title = getString(R.string.lb_user_detail)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            etUserName.setText(user.userName)
            etUserAge.setText(user.userAge.toString())
            val bitmap = BitmapFactory.decodeByteArray(user.avatar, 0, user.avatar!!.size)
            imgUser.setImageBitmap(bitmap)

            imgUser.setOnClickListener {
                pickImageLauncher.launch(arrayOf("image/*"))
            }

            btnDeleteUser.setOnClickListener {
                AlertDialog.Builder(this@UserDetailActivity)
                    .setTitle(R.string.lb_toast_notify_delete)
                    .setMessage(R.string.lb_toast_delete)
                    .setPositiveButton(R.string.lb_allow) { _, _ ->
                        userViewModel.deleteUser(
                            UserDB(
                                id = user.id,
                                userName = user.userName,
                                userAge = user.userAge.toInt(),
                                avatar = user.avatar
                            )
                        )
                    }
                    .setNegativeButton(R.string.lb_cancel) { _, _ -> }
                    .show()
            }

            btnUpdateUser.setOnClickListener {
                val name = etUserName.text.toString()
                val ageText = etUserAge.text.toString()
                val avatar = selectedImage

                val age = try {
                    ageText.toInt()
                } catch (e: NumberFormatException) {
                    e.printStackTrace()
                    return@setOnClickListener
                }

                if (name.isBlank() || ageText.isBlank() || avatar == null) {
                    toast(R.string.lb_user_data_null, Toast.LENGTH_SHORT)
                    return@setOnClickListener
                }

                userViewModel.updateUser(
                    UserDB(
                        id = user.id,
                        userName = name,
                        userAge = age,
                        avatar = selectedImage ?: user.avatar
                    )
                )
            }
        }
    }

    private val pickImageLauncher =
        registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri: Uri? ->
            uri?.let {
                try {
                    contentResolver.takePersistableUriPermission(
                        it,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                val imageBytes = ConvertData(this@UserDetailActivity).uriToByteArray(it)
                selectedImage = imageBytes

                val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                binding.imgUser.setImageBitmap(bitmap)

            }
        }

    private fun observeViewModel() {
        binding.apply {
            userViewModel.message.observe(this@UserDetailActivity) {
                when (it) {
                    DELETE_USER_SUCCESS -> {
                        toast(R.string.lb_user_delete_success, Toast.LENGTH_SHORT)
                        finish()
                    }

                    UPDATE_USER_SUCCESS -> {
                        toast(R.string.lb_user_update_success, Toast.LENGTH_SHORT)
                        finish()
                    }
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
