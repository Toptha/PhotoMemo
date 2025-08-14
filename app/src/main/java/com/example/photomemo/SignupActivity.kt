package com.example.photomemo

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class SignupActivity : AppCompatActivity() {

    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var confirmPasswordInput: EditText
    private lateinit var fullNameInput: EditText
    private lateinit var phoneInput: EditText
    private lateinit var genderRadioGroup: RadioGroup
    private lateinit var signupButton: Button
    private lateinit var backToLoginText: TextView

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        dbHelper = DatabaseHelper(this)

        fullNameInput = findViewById(R.id.fullNameEditText)
        phoneInput = findViewById(R.id.phoneEditText)
        emailInput = findViewById(R.id.emailEditText)
        passwordInput = findViewById(R.id.passwordEditText)
        confirmPasswordInput = findViewById(R.id.confirmPasswordEditText)
        genderRadioGroup = findViewById(R.id.genderGroup)
        signupButton = findViewById(R.id.signupButton)
        backToLoginText = findViewById(R.id.backToLoginText)

        signupButton.setOnClickListener {
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()
            val confirmPassword = confirmPasswordInput.text.toString().trim()
            val fullName = fullNameInput.text.toString().trim()
            val phone = phoneInput.text.toString().trim()
            val genderId = genderRadioGroup.checkedRadioButtonId

            if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()
                || fullName.isEmpty() || phone.isEmpty() || genderId == -1
            ) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val gender = findViewById<RadioButton>(genderId).text.toString()

            val success = dbHelper.insertUser(email, password, fullName, phone, gender)

            if (success) {
                val prefs = getSharedPreferences("PhotoMemoPrefs", MODE_PRIVATE)
                prefs.edit()
                    .putBoolean("isLoggedIn", true)
                    .putString("loggedInUserEmail", email)
                    .apply()

                Toast.makeText(this, "Signup successful!", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Error: Email already exists", Toast.LENGTH_SHORT).show()
            }
        }

        backToLoginText.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}
