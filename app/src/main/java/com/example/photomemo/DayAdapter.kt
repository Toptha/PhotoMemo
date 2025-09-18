package com.example.photomemo

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DayAdapter(private val days: List<String>) : RecyclerView.Adapter<DayAdapter.DayViewHolder>() {

    private val scratchedDays = mutableSetOf<Int>() // to keep track of scratched days

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_day, parent, false)
        return DayViewHolder(view)
    }

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        val day = days[position]
        holder.dayText.text = day

        // Check if scratched
        if (scratchedDays.contains(position)) {
            holder.dayText.paintFlags = holder.dayText.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            holder.dayText.paintFlags = holder.dayText.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }

        holder.itemView.setOnClickListener {
            if (scratchedDays.contains(position)) {
                scratchedDays.remove(position)
            } else {
                scratchedDays.add(position)
            }
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int = days.size

    class DayViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dayText: TextView = itemView.findViewById(R.id.dayText)
    }
}
