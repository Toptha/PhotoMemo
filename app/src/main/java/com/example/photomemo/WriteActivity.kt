package com.example.photomemo

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class WriteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)

        val entryDate = findViewById<TextView>(R.id.entryDate)
        val currentDate = SimpleDateFormat("MMMM d, yyyy", Locale.getDefault()).format(Date())
        entryDate.text = currentDate

        val saveButton = findViewById<ImageButton>(R.id.saveButton)
        val journalEditText = findViewById<EditText>(R.id.journalEditText)

        saveButton.setOnClickListener {
            val intent = Intent(this@WriteActivity, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
