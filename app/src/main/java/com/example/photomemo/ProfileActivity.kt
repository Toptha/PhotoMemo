package com.example.photomemo

import android.content.Context
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        textFullName = findViewById(R.id.textFullName)
        textEmail = findViewById(R.id.textEmail)
        textPhone = findViewById(R.id.textPhone)
        textGender = findViewById(R.id.textGender)
        btnLogout = findViewById(R.id.btnLogout)

        val sharedPrefs = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val email = sharedPrefs.getString("loggedInUserEmail", null)

        if (email != null) {
            val fullName = sharedPrefs.getString("${email}_fullName", "N/A")
            val phone = sharedPrefs.getString("${email}_phone", "N/A")
            val gender = sharedPrefs.getString("${email}_gender", "N/A")

            textFullName.text = "Full Name:\n $fullName"
            textEmail.text = "Email:\n $email"
            textPhone.text = "Phone:\n +91 $phone"
            textGender.text = "Gender:\n $gender"
        } else {
            textFullName.text = "Full Name:\nN/A"
            textEmail.text = "Email:\nN/A"
            textPhone.text = "Phone:\nN/A"
            textGender.text = "Gender:\nN/A"
        }

        btnLogout.setOnClickListener {
            sharedPrefs.edit().clear().apply()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}
