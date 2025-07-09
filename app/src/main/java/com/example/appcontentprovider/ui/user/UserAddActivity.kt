package com.example.appcontentprovider.ui.user

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.appcontentprovider.R
import com.example.appcontentprovider.databinding.ActivityUserAddBinding
import com.example.appcontentprovider.extention.toast
import com.example.appcontentprovider.utils.ADD_USER_SUCCESS
import com.example.appcontentprovider.utils.ConvertData

class UserAddActivity : AppCompatActivity() {
    private val binding by lazy { ActivityUserAddBinding.inflate(layoutInflater) }
    private val userViewModel: UserViewModel by viewModels()

    private var selectedImage: ByteArray? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initUI()
        observeViewModel()
    }

    private fun initUI() {
        binding.apply {
            setSupportActionBar(toolbar)
            supportActionBar?.title = getString(R.string.lb_user_add)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            btnAvatar.setOnClickListener {
                pickImageLauncher.launch(arrayOf("image/*"))
            }

            btnAdd.setOnClickListener {
                val userName = etUserName.text.toString()
                val userAge = etUserAge.text.toString()
                val cityName = tvCityName.text.toString()
                val avatar = selectedImage

                val age = try {
                    userAge.toInt()
                } catch (e: NumberFormatException) {
                    Toast.makeText(
                        this@UserAddActivity,
                        "Invalid age format ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
                if (userName.isBlank() || avatar == null || userAge.isBlank()) {
                    toast(R.string.lb_user_data_null, Toast.LENGTH_SHORT)
                    return@setOnClickListener
                }

                userViewModel.insertUser(
                    UserDB(
                        userName = userName,
                        userAge = age,
                        avatar = avatar,
                        cityName = cityName
                    )
                )
            }
        }
    }

    private fun observeViewModel() {
        binding.apply {
            userViewModel.loading.observe(this@UserAddActivity) {
                progressCircular.isVisible = it
            }

            userViewModel.message.observe(this@UserAddActivity) {
                when (it) {
                    ADD_USER_SUCCESS -> {
                        toast(R.string.lb_user_add_success, Toast.LENGTH_SHORT)
                        finish()
                    }
                }
            }
        }
    }

    private fun handelSaveImage(uri: Uri?) {
        uri?.let {
            val originalBytes = ConvertData(this@UserAddActivity).uriToByteArray(it)
            if (originalBytes != null) {
                val originalBitmap =
                    BitmapFactory.decodeByteArray(originalBytes, 0, originalBytes.size)
                Log.d("Origin image", originalBytes.size.toString())

                val resizedBitmap = ConvertData(this@UserAddActivity).resizeBitmap(originalBitmap)
                selectedImage = ConvertData(this@UserAddActivity).bitmapToByteArray(resizedBitmap)
                Log.d("Resized image", selectedImage!!.size.toString())

                binding.btnAvatar.setImageBitmap(resizedBitmap)
            }
        }
    }

    private val pickImageLauncher =
        registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri: Uri? ->
            handelSaveImage(uri)
        }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
