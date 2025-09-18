package com.example.photomemo

import android.content.Context
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class WriteActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)

        dbHelper = DatabaseHelper(this)

        val entryDate: TextView = findViewById(R.id.entryDate)
        val journalEditText: EditText = findViewById(R.id.journalEditText)
        val saveButton: ImageButton = findViewById(R.id.saveButton)

        // Show current date
        val currentDate = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault()).format(Date())
        entryDate.text = currentDate

        saveButton.setOnClickListener {
            val content = journalEditText.text.toString().trim()
            if (content.isEmpty()) {
                Toast.makeText(this, "Please write something first", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val sharedPref = getSharedPreferences("user_session", Context.MODE_PRIVATE)
            val email = sharedPref.getString("email", null)

            if (email != null) {
                val success = dbHelper.insertNote(email, currentDate, content)
                if (success) {
                    Toast.makeText(this, "Note saved!", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Error saving note", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
