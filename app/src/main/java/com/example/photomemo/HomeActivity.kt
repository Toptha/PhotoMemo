package com.example.photomemo

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val stickyCapture = findViewById<LinearLayout>(R.id.stickyCapture)
        val stickyWrite = findViewById<LinearLayout>(R.id.stickyWrite)
        val stickySaved = findViewById<LinearLayout>(R.id.stickySaved)
        val stickyProfile = findViewById<LinearLayout>(R.id.stickyProfile)
        val bottomMenu = findViewById<BottomNavigationView>(R.id.bottomMenu)

        stickyCapture.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (cameraIntent.resolveActivity(packageManager) != null) {
                startActivity(cameraIntent)
            } else {
                Toast.makeText(this, "No camera app found!", Toast.LENGTH_SHORT).show()
            }
        }

        stickyWrite.setOnClickListener {
            startActivity(Intent(this, WriteActivity::class.java))
        }

        stickySaved.setOnClickListener {
            startActivity(Intent(this, SavedEntriesActivity::class.java))
        }

        stickyProfile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }


        bottomMenu.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    true
                }
                R.id.menu_saved -> {
                    startActivity(Intent(this, WriteActivity::class.java))
                    true
                }
                R.id.menu_settings -> {
                    Toast.makeText(this, "Open diary settings", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.menu_logout -> {
                    val prefs = getSharedPreferences("PhotoMemoPrefs", MODE_PRIVATE)
                    prefs.edit().clear().apply()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                    true
                }
                else -> false
            }
        }
    }
}
