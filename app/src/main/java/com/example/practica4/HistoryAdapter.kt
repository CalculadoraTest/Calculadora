package com.example.practica4

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class HistoryAdapter(private val listaDatos:List<Operation>):RecyclerView.Adapter<HistoryView>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryView {
        val context:Context = parent.context
        val inflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.elemento_historial, parent, false)
        return HistoryView(view)
    }

    override fun getItemCount(): Int {
        return listaDatos.size
    }

    override fun onBindViewHolder(holder: HistoryView, position: Int) {
        val dato:Operation = listaDatos[position]
        holder.resultado.text = dato.result.toString()
        (position + 1).toString().also { holder.number.text = it }
        var operator = when(dato.operation){
            Operaciones.NING -> "Error"
            Operaciones.MAS -> "+"
            Operaciones.MENOS -> "-"
            Operaciones.MULTIPLICACION -> "x"
            Operaciones.DIVISION -> "/"
            Operaciones.MODULO -> "%"
            Operaciones.MASMENOS -> "±"
        }
        "${dato.operatorOne} ${operator} ${dato.operatorTwo}".also { holder.operatorOne.text = it }

    }


}