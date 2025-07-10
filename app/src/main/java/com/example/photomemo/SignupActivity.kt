package com.example.photomemo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SignupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val emailField = findViewById<EditText>(R.id.signupEmail)
        val passwordField = findViewById<EditText>(R.id.signupPassword)
        val signupNowButton = findViewById<Button>(R.id.signupNowButton)
        val backToLoginText = findViewById<TextView>(R.id.backToLoginText)

        signupNowButton.setOnClickListener {
            val email = emailField.text.toString().trim()
            val password = passwordField.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            } else {
                val sharedPref = getSharedPreferences("UserData", MODE_PRIVATE)
                sharedPref.edit().apply {
                    putString("email", email)
                    putString("password", password)
                    apply()
                }
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }
        }

        backToLoginText.setOnClickListener {
            finish()
        }
    }
}
