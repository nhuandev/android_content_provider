package com.example.client

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.client.databinding.ActivityMainBinding
import com.example.client.ui.user.UserActivity

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        binding.apply {
            fab.setOnClickListener {
                val intent = Intent(this@MainActivity, UserActivity::class.java)
                startActivity(intent)
            }
        }
    }
}