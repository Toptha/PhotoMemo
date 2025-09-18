package com.example.photomemo

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class HomeActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val stickyCapture = findViewById<LinearLayout>(R.id.stickyCapture)
        val stickyWrite = findViewById<LinearLayout>(R.id.stickyWrite)
        val stickySaved = findViewById<LinearLayout>(R.id.stickySaved)
        val stickyProfile = findViewById<LinearLayout>(R.id.stickyProfile)
        val bottomMenu = findViewById<BottomNavigationView>(R.id.bottomMenu)

        drawerLayout = findViewById(R.id.drawerLayout)
        navigationView = findViewById(R.id.navigationView)

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
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, SavedEntriesFragment())
                .addToBackStack(null)
                .commit()
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
                    startActivity(Intent(this, SavedEntriesActivity::class.java))
                    true
                }
                R.id.menu_settings -> {
                    drawerLayout.openDrawer(navigationView)
                    true
                }
                R.id.menu_more -> {
                    showPopupMenu(bottomMenu)
                    true
                }
                else -> false
            }
        }

        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.drawer_settings -> {
                    Toast.makeText(this, "Settings clicked", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.drawer_help -> {
                    Toast.makeText(this, "Help clicked", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.drawer_about -> {
                    Toast.makeText(this, "About clicked", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }.also {
                drawerLayout.closeDrawers()
            }
        }
    }

    private fun showPopupMenu(anchor: View) {
        val popup = PopupMenu(this, anchor)
        popup.menuInflater.inflate(R.menu.menu_popup, popup.menu)

        popup.setOnMenuItemClickListener { item: MenuItem ->
            when (item.itemId) {
                R.id.popup_settings -> {
                    Toast.makeText(this, "Trash clicked", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.popup_logout -> {
                    val prefs = getSharedPreferences("PhotoMemoPrefs", MODE_PRIVATE)
                    prefs.edit().clear().apply()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                    true
                }
                R.id.popup_logs -> {
                    startActivity(Intent(this, LogsActivity::class.java))
                    true
                }
                else -> false
            }
        }

        popup.show()
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(navigationView)) {
            drawerLayout.closeDrawers()
        } else {
            super.onBackPressed()
        }
    }
}
