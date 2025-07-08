package com.example.appcontentprovider.ui.user

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appcontentprovider.R
import com.example.appcontentprovider.databinding.ActivityUserBinding
import com.example.appcontentprovider.extention.isHasPermission
import com.example.appcontentprovider.utils.INTENT_USER

class UserActivity : AppCompatActivity() {
    private val binding by lazy { ActivityUserBinding.inflate(layoutInflater) }
    private var mPermission = Manifest.permission.READ_EXTERNAL_STORAGE
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    private lateinit var adapterUser: UserAdapter
    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initPermissionLauncher()
        initUI()
        observeViewModel()
    }

    override fun onResume() {
        super.onResume()
        observeViewModel()
    }

    private fun initUI() {
        binding.apply {
            adapterUser = UserAdapter(emptyList(), {
                val intent = Intent(this@UserActivity, UserDetailActivity::class.java)
                intent.putExtra(INTENT_USER, it)
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                startActivity(intent)
            })
            recUser.layoutManager = LinearLayoutManager(this@UserActivity)
            recUser.adapter = adapterUser

            btnAddUser.setOnClickListener {
                if (isHasReadPermission()) {
                    startActivity(Intent(this@UserActivity, UserAddActivity::class.java))
                } else {
                    requestReadPermission()
                }
            }
        }
    }

    private fun observeViewModel() {
        binding.apply {
            userViewModel.getAllUser.observe(this@UserActivity) {
                if (it.isEmpty()) {
                    tvDataNull.isVisible = true
                    recUser.isVisible = false
                } else {
                    tvDataNull.isVisible = false
                    recUser.isVisible = true
                    adapterUser.updateData(it)
                }
            }

            userViewModel.loading.observe(this@UserActivity) {
                progressCircular.isVisible = it
            }
        }
    }

    private fun initPermissionLauncher() {
        requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                when {
                    isGranted -> startActivity(
                        Intent(
                            this@UserActivity,
                            UserAddActivity::class.java
                        )
                    )

                    shouldShowRequestPermissionRationale(mPermission) -> {
                        AlertDialog.Builder(this@UserActivity)
                            .setTitle(R.string.lb_storage_permission)
                            .setMessage(R.string.lb_message_permission_gallery)
                            .setPositiveButton(R.string.lb_allow) { _, _ ->
                                requestReadPermission()
                            }
                            .setNegativeButton(R.string.lb_cancel) { dialog, _ ->
                                dialog.dismiss()
                            }
                            .create()
                            .show()
                    }

                    else -> {
                        AlertDialog.Builder(this@UserActivity)
                            .setTitle(R.string.lb_permission)
                            .setMessage(R.string.lb_message_permission_gallery)
                            .setPositiveButton(R.string.lb_setting) { _, _ ->
                                gotoSetting()
                            }
                            .setNegativeButton(R.string.lb_cancel) { dialog, _ ->
                                dialog.dismiss()
                            }
                            .create()
                            .show()
                    }
                }
            }
    }

    private fun gotoSetting() {
        val intent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", packageName, null)
        )
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(
            intent
        )
    }

    private fun isHasReadPermission(): Boolean {
        val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_IMAGES
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }
        return isHasPermission(permission)
    }

    private fun requestReadPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            mPermission = Manifest.permission.READ_MEDIA_IMAGES
        }
        requestPermissionLauncher.launch(mPermission)
    }
}
