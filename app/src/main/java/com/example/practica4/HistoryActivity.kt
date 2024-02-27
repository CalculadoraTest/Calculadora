package com.example.practica4

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.Serializable

class HistoryActivity: AppCompatActivity() {

    private lateinit var recyclerView:RecyclerView
    private lateinit var adaptador:HistoryAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val testOperation = listOf<Operation>(
            Operation(5,5,Operaciones.MAS,10),
            Operation(5,5,Operaciones.MULTIPLICACION, 25)
        )
        setContentView(R.layout.historial)
        val hist:ArrayList<Operation> = intent.getSerializableExtra("hist")  as? ArrayList<Operation> ?: ArrayList()
        val returnButton: ImageButton = findViewById(R.id.histReturn)

        returnButton.setOnClickListener{
            finish()
        }



        recyclerView = findViewById(R.id.recycleView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adaptador = HistoryAdapter(hist.toList())
        recyclerView.adapter = adaptador
    }
}