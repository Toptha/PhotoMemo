package com.example.photomemo

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SavedEntriesFragment : Fragment() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NoteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_saved_entries, container, false)

        dbHelper = DatabaseHelper(requireContext())
        recyclerView = view.findViewById(R.id.recyclerViewNotes)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val sharedPref = requireContext().getSharedPreferences("user_session", Context.MODE_PRIVATE)
        val email = sharedPref.getString("email", null)

        if (email != null) {
            val notes = dbHelper.getNotes(email)
            adapter = NoteAdapter(notes)
            recyclerView.adapter = adapter
        } else {
            Toast.makeText(requireContext(), "No user logged in", Toast.LENGTH_SHORT).show()
        }

        return view
    }
}
