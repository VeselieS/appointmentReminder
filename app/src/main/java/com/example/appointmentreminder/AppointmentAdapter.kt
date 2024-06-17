package com.example.appointmentreminder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class AppointmentAdapter(private val appointments: MutableList<Appoint>) : RecyclerView.Adapter<AppointmentAdapter.AppointmentViewHolder>() {
    class AppointmentViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.appTitle)
        val date: TextView = view.findViewById(R.id.appDate)
        val time: TextView = view.findViewById(R.id.appTime)
        val client_name: TextView = view.findViewById(R.id.app_client_name)
        val client_email: TextView = view.findViewById(R.id.app_client_email)
        val pictureImageView: ImageView = view.findViewById(R.id.app_client_photo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.appointment_item, parent, false)
        return AppointmentViewHolder(view)
    }

    override fun onBindViewHolder(holder: AppointmentViewHolder, position: Int) {
        val appointment = appointments[position]
        holder.title.text = appointment.title
        holder.date.text = appointment.date
        holder.time.text = appointment.time
        holder.client_name.text = appointment.client_name
        holder.client_email.text = appointment.client_email
        Glide.with(holder.pictureImageView.context).load(appointment.pictureUrl).into(holder.pictureImageView)
    }

    override fun getItemCount() = appointments.size
}

