package com.example.appointmentreminder

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AppointmentAdapter
    private var appointments: MutableList<Appoint> = mutableListOf()
    private val PREFS_NAME = "appointments_prefs"
    private val APPOINTMENTS_KEY = "appointments"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val gson = Gson()
        val json = prefs.getString(APPOINTMENTS_KEY, "")
        if (json!!.isNotEmpty()) {
            val type = object : TypeToken<List<Appoint>>() {}.type
            appointments = gson.fromJson(json, type)
        }

        recyclerView = findViewById(R.id.recyclerView)
        adapter = AppointmentAdapter(appointments)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val addAppointment: FloatingActionButton = findViewById<FloatingActionButton>(R.id.fabAdd)
        addAppointment.setOnClickListener() {
            val intent = Intent(this, AddAppointment::class.java)
            startActivityForResult(intent, 1)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            val title = data.getStringExtra("title")
            val date = data.getStringExtra("date")
            val time = data.getStringExtra("time")
            val client_name = data.getStringExtra("client_name")
            val client_email = data.getStringExtra("client_email")
            val client_photo = data.getStringExtra("client_photo")

            if (title != null && date != null && time != null && client_name != null && client_email != null && client_photo != null) {
                val appointment =
                    Appoint(title, date, time, client_name, client_email, client_photo)
                appointments.add(appointment)
                adapter.notifyDataSetChanged()

                val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                val editor = prefs.edit()
                val gson = Gson()
                val json = gson.toJson(appointments)
                editor.putString(APPOINTMENTS_KEY, json)
                editor.apply()
            }
        }
    }
}
