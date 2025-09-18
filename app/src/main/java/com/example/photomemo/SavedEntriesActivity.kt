package com.example.photomemo

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SavedEntriesActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved_entries)

        dbHelper = DatabaseHelper(this)
        recyclerView = findViewById(R.id.recyclerViewNotes)

        recyclerView.layoutManager = LinearLayoutManager(this)

        val sharedPref = getSharedPreferences("user_session", Context.MODE_PRIVATE)
        val email = sharedPref.getString("email", null)

        if (email != null) {
            val notes = dbHelper.getNotes(email)
            adapter = NoteAdapter(notes)
            recyclerView.adapter = adapter
        } else {
            Toast.makeText(this, "No user logged in", Toast.LENGTH_SHORT).show()
        }
    }
}
