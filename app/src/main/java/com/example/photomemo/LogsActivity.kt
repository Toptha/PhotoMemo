package com.example.photomemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class LogsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logs)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewLogs)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Example: 30 days
        val daysList = (1..30).map { "Day $it" }
        val adapter = DayAdapter(daysList)
        recyclerView.adapter = adapter
    }
}
