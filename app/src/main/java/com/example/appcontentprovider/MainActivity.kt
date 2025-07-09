package com.example.appcontentprovider

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.appcontentprovider.databinding.ActivityMainBinding
import com.example.appcontentprovider.ui.product.ProductActivity

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
                AlertDialog.Builder(this@MainActivity)
                    .setTitle("Content Provider")
                    .setMessage("You want to add user or product?")
                    .setPositiveButton("Product") { _, _ ->
                        val intent = Intent(this@MainActivity, ProductActivity::class.java)
                        startActivity(intent)
                    }
                    .show()
            }
        }
    }
}
