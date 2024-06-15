package com.example.appointmentreminder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ClientAdapter(var clients: List<Client>, private val listener: OnClientClickListener) : RecyclerView.Adapter<ClientAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.client_name)
        val emailTextView: TextView = view.findViewById(R.id.client_email)
        val pictureImageView: ImageView = view.findViewById(R.id.client_photo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.client_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val client = clients[position]
        holder.nameTextView.text = client.name
        holder.emailTextView.text = client.email
        Glide.with(holder.pictureImageView.context).load(client.pictureUrl).into(holder.pictureImageView)

        holder.itemView.setOnClickListener {
            listener.onClientClick(client)
        }
    }

    override fun getItemCount() = clients.size
}