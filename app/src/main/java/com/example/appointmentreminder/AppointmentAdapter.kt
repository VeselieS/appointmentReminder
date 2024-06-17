package com.example.appointmentreminder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AppointmentAdapter(private val appointments: MutableList<Appoint>) : RecyclerView.Adapter<AppointmentAdapter.AppointmentViewHolder>() {
    class AppointmentViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.appTitle)
        val date: TextView = view.findViewById(R.id.appDate)
        val time: TextView = view.findViewById(R.id.appTime)
        val client: TextView = view.findViewById(R.id.app_client_name)
        // Добавьте другие поля, если они есть
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.appointment_item, parent, false)
        return AppointmentViewHolder(view)
    }

    override fun onBindViewHolder(holder: AppointmentViewHolder, position: Int) {
        val appointment = appointments[position]
        holder.title.text = appointment.title
        holder.date.text = appointment.date.toString() // Преобразуйте дату в строку
        holder.time.text = appointment.time.toString() // Преобразуйте время в строку
        holder.client.text = appointment.client // Используйте нужные поля клиента
        // Заполните другие поля, если они есть
    }

    override fun getItemCount() = appointments.size
}

