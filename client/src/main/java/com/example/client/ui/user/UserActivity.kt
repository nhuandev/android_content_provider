package com.example.client.ui.user

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.client.R
import com.example.client.Utils.Constants
import com.example.client.databinding.ActivityUserBinding
import com.example.client.extention.toast
import kotlin.getValue

class UserActivity : AppCompatActivity() {
    private val binding by lazy { ActivityUserBinding.inflate(layoutInflater) }
    private lateinit var adapterUser: UserAdapter

    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initView()
        observeViewModel()
    }

    private fun initView() {
        adapterUser = UserAdapter(emptyList()) {
            val intent = Intent(this@UserActivity, UserAddActivity::class.java)
            intent.putExtra("KEY_USER_ID", it)
            intent.putExtra("KEY_CHECK_FEATURE", 1) // 1 is update
            startActivity(intent)
        }

        binding.apply {
            rvUser.adapter = adapterUser
            rvUser.layoutManager = LinearLayoutManager(this@UserActivity)

            btnAdd.setOnClickListener {
                val intent = Intent(this@UserActivity, UserAddActivity::class.java)
                intent.putExtra("KEY_CHECK_FEATURE", 0) // 0 is add
                startActivity(intent)
            }
        }
    }

    private fun observeViewModel() {
        userViewModel.users.observe(this@UserActivity) {
            adapterUser.updateData(it)
        }
    }

    override fun onResume() {
        super.onResume()
        userViewModel.loadUsers()
    }
}
