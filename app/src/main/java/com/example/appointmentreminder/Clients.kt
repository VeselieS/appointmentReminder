package com.example.appointmentreminder

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.JsonHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class Clients : AppCompatActivity(), OnClientClickListener {

    private lateinit var clientsAdapter: ClientAdapter
    private lateinit var loadingView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_clients)

        loadingView = findViewById<TextView>(R.id.loadingView)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val backButton: Button = findViewById<Button>(R.id.bBack)

        backButton.setOnClickListener() {
            finish()
        }

        loadClients()

        clientsAdapter = ClientAdapter(emptyList(), this)
        findViewById<RecyclerView>(R.id.clientsRecycleView).adapter = clientsAdapter

    }

    override fun onClientClick(client: Client) {
        val intent: Intent = Intent()
        intent.putExtra("name", client.name)
        intent.putExtra("email", client.email)
        intent.putExtra("photo", client.pictureUrl)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private fun loadClients() {
        loadingView.visibility = View.VISIBLE
        val client = AsyncHttpClient()
        val url = "https://randomuser.me/api/?results=15"

        client.get(url, object : JsonHttpResponseHandler() {
            @SuppressLint("NotifyDataSetChanged")
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>?,
                response: JSONObject?
            ) {
                super.onSuccess(statusCode, headers, response)
                loadingView.visibility = View.GONE
                val results = response?.getJSONArray("results")
                val clients = mutableListOf<Client>()
                for (i in 0 until (results?.length() ?: 0)) {
                    val result = results?.getJSONObject(i)
                    val nameObject = result?.getJSONObject("name")
                    val name = "${nameObject?.getString("first")} ${nameObject?.getString("last")}"
                    val email = result?.getString("email") ?: ""
                    val pictureObject = result?.getJSONObject("picture")
                    val pictureUrl = pictureObject?.getString("large") ?: ""
                    val client = Client(name, email, pictureUrl)
                    clients.add(client)
                }
                clientsAdapter.clients = clients
                clientsAdapter.notifyDataSetChanged()
            }
        })
    }


}