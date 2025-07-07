package com.example.photomemo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SignupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val backToLoginText = findViewById<TextView>(R.id.backToLoginText)
        val signupNowButton = findViewById<Button>(R.id.signupNowButton)

        backToLoginText.setOnClickListener {
            finish()
        }

        signupNowButton.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
