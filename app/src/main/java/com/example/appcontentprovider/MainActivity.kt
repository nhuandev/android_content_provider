package com.example.appcontentprovider

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.appcontentprovider.databinding.ActivityMainBinding
import com.example.appcontentprovider.ui.user.UserActivity

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initUI()
    }

    private fun initUI() {
        binding.apply {
            fab.setOnClickListener {
                val intent = Intent(this@MainActivity, UserActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
