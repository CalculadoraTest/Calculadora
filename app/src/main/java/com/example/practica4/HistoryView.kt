package com.example.practica4

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class HistoryView(itemView: View):RecyclerView.ViewHolder(itemView) {
    val operatorOne: TextView = itemView.findViewById(R.id.operatorOne);
    val operatorTwo: TextView = itemView.findViewById(R.id.operatorTwo);
    val operation:TextView = itemView.findViewById(R.id.operation);
    val resultado:TextView = itemView.findViewById(R.id.resHist)
}