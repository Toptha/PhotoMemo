package com.example.photomemo

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {
    private lateinit var textFullName: TextView
    private lateinit var textEmail: TextView
    private lateinit var textPhone: TextView
    private lateinit var textGender: TextView
    private lateinit var btnLogout: TextView

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        dbHelper = DatabaseHelper(this)

        textFullName = findViewById(R.id.textFullName)
        textEmail = findViewById(R.id.textEmail)
        textPhone = findViewById(R.id.textPhone)
        textGender = findViewById(R.id.textGender)
        btnLogout = findViewById(R.id.btnLogout)

        val prefs = getSharedPreferences("PhotoMemoPrefs", MODE_PRIVATE)
        val email = prefs.getString("loggedInUserEmail", null)

        if (email != null) {
            val user = dbHelper.getUserDetails(email)
            if (user != null) {
                textFullName.text = "Full Name:\n${user.fullName}"
                textEmail.text = "Email:\n$email"
                textPhone.text = "Phone:\n+91 ${user.phone}"
                textGender.text = "Gender:\n${user.gender}"
            } else {
                setNA()
            }
        } else {
            setNA()
        }

        btnLogout.setOnClickListener {
            prefs.edit().clear().apply()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun setNA() {
        textFullName.text = "Full Name:\nN/A"
        textEmail.text = "Email:\nN/A"
        textPhone.text = "Phone:\nN/A"
        textGender.text = "Gender:\nN/A"
    }
}
