package com.example.appointmentreminder

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AppointmentAdapter
    private val appointments: MutableList<Appoint> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        adapter = AppointmentAdapter(appointments)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val addAppointment: FloatingActionButton = findViewById<FloatingActionButton>(R.id.fabAdd)
        addAppointment.setOnClickListener() {
            val intent = Intent(this, AddAppointment::class.java)
            startActivity(intent)
        }

        val title = intent.getStringExtra("title")
        val date = intent.getStringExtra("date")
        val time = intent.getStringExtra("time")
        val client_name = intent.getStringExtra("client_name")
        val client_email = intent.getStringExtra("client_email")
        val client_photo = intent.getStringExtra("client_photo")


        if (title != null && date != null && time != null && client_name != null && client_email != null && client_photo != null) {
            val appointment = Appoint(title, date, time, client_name, client_email, client_photo)
            appointments.add(appointment)
            adapter.notifyDataSetChanged()
        }
    }
}
