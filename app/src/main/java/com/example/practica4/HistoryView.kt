package com.example.practica4

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class HistoryView(itemView: View):RecyclerView.ViewHolder(itemView) {
    val operatorOne: TextView = itemView.findViewById(R.id.operatorOne);
    val resultado:TextView = itemView.findViewById(R.id.resHist)
    val number:TextView = itemView.findViewById(R.id.number)
}