package com.example.photomemo

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity

class SignupFragment : Fragment() {

    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var confirmPasswordInput: EditText
    private lateinit var fullNameInput: EditText
    private lateinit var phoneInput: EditText
    private lateinit var genderRadioGroup: RadioGroup
    private lateinit var signupButton: Button
    private lateinit var backToLoginText: TextView
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_signup, container, false)

        dbHelper = DatabaseHelper(requireContext())
        fullNameInput = view.findViewById(R.id.fullNameEditText)
        phoneInput = view.findViewById(R.id.phoneEditText)
        emailInput = view.findViewById(R.id.emailEditText)
        passwordInput = view.findViewById(R.id.passwordEditText)
        confirmPasswordInput = view.findViewById(R.id.confirmPasswordEditText)
        genderRadioGroup = view.findViewById(R.id.genderGroup)
        signupButton = view.findViewById(R.id.signupButton)
        backToLoginText = view.findViewById(R.id.backToLoginText)

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
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(requireContext(), "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val gender = view.findViewById<RadioButton>(genderId).text.toString()

            // ✅ Correct order: (name, email, password, phone, gender)
            val success = dbHelper.insertUser(fullName, email, password, phone, gender)

            if (success) {
                // ✅ Use the same prefs as WriteActivity
                val prefs = requireActivity().getSharedPreferences("user_session", AppCompatActivity.MODE_PRIVATE)
                prefs.edit()
                    .putBoolean("isLoggedIn", true)
                    .putString("email", email)
                    .apply()

                Toast.makeText(requireContext(), "Signup successful!", Toast.LENGTH_SHORT).show()
                startActivity(Intent(requireContext(), HomeActivity::class.java))
                requireActivity().finish()
            } else {
                Toast.makeText(requireContext(), "Error: Email already exists", Toast.LENGTH_SHORT).show()
            }
        }

        backToLoginText.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        return view
    }
}
