package com.example.photomemo

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val stickyCapture = findViewById<LinearLayout>(R.id.stickyCapture)
        val stickyWrite = findViewById<LinearLayout>(R.id.stickyWrite)
        val stickySaved = findViewById<LinearLayout>(R.id.stickySaved)
        val stickyProfile = findViewById<LinearLayout>(R.id.stickyProfile)

        stickyCapture.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (cameraIntent.resolveActivity(packageManager) != null) {
                startActivity(cameraIntent)
            } else {
                Toast.makeText(this, "No camera app found!", Toast.LENGTH_SHORT).show()
            }
        }

        stickyWrite.setOnClickListener {
            val intent = Intent(this, WriteActivity::class.java)
            startActivity(intent)
        }

        stickySaved.setOnClickListener {
            val intent = Intent(this, SavedEntriesActivity::class.java)
            startActivity(intent)
        }

        stickyProfile.setOnClickListener {
            val intent = Intent(Settings.ACTION_SETTINGS)
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            } else {
                Toast.makeText(this, "No app found to handle profile intent!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
