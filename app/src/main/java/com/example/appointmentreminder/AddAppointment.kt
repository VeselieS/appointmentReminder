package com.example.appointmentreminder

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.InputType
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class AddAppointment : AppCompatActivity() {
    private lateinit var chooseClient: TextInputEditText
    private lateinit var client_email: TextView
    private lateinit var photo: String
    private val CLIENT_REQUEST_CODE = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_appointment)

        val editTime: EditText = findViewById(R.id.editTextTime)
        val editDate: EditText = findViewById(R.id.editTextDate)
        val editTitle: EditText = findViewById(R.id.editTextTitle)
        editTitle.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(25))
        val backButton: Button = findViewById<Button>(R.id.bBack)
        val createButton: Button = findViewById<Button>(R.id.bCreate)
        val timeButton: ImageButton = findViewById<ImageButton>(R.id.imageButtonTime)
        val dateButton: ImageButton = findViewById(R.id.imageButtonDate)
        val chooseClientLayout: TextInputLayout =
            findViewById<TextInputLayout>(R.id.clientInputLayout)
        chooseClient = chooseClientLayout.editText as TextInputEditText
        client_email = findViewById<TextView>(R.id.client_email_add)

        editTime.inputType = InputType.TYPE_NULL
        editDate.inputType = InputType.TYPE_NULL

        chooseClient.invalidate()

        backButton.setOnClickListener() {
            finish()
        }

        timeButton.setOnClickListener {
            val c = Calendar.getInstance()
            val hour = c.get(Calendar.HOUR_OF_DAY)
            val minute = c.get(Calendar.MINUTE)

            val timePickerDialog = TimePickerDialog(
                this,
                { _, selectedHour, selectedMinute ->
                    val selectedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
                    editTime.setText(selectedTime)
                }, hour, minute, true
            )

            timePickerDialog.show()
        }

        dateButton.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                { _, selectedYear, selectedMonth, selectedDay ->
                    val selectedDate = String.format(
                        "%02d/%02d/%04d",
                        selectedDay,
                        selectedMonth + 1,
                        selectedYear
                    )
                    editDate.setText(selectedDate)
                }, year, month, day
            )

            datePickerDialog.show()
        }

        chooseClient.setOnClickListener() {
            val intent = Intent(this, Clients::class.java)
            startActivityForResult(intent, CLIENT_REQUEST_CODE)
        }

        val fields = listOf(editTitle, editDate, chooseClient)
        createButton.isEnabled = false

        val watcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                createButton.isEnabled = fields.all { it.text.toString().isNotEmpty() }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }

        fields.forEach { it.addTextChangedListener(watcher) }


        createButton.setOnClickListener {
            val title = editTitle.text.toString()
            val date = editDate.text.toString()
            val time = editTime.text.toString()
            val client_name = chooseClient.text.toString()
            val client_email = client_email.text
            val photo = photo

            val intent = Intent(this, MainActivity::class.java).apply {
                putExtra("title", title)
                putExtra("date", date)
                putExtra("time", time)
                putExtra("client_name", client_name)
                putExtra("client_email", client_email)
                putExtra("client_photo", photo)
            }
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    @Deprecated("Deprecated in Java")
    @SuppressLint("SetTextI18n")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CLIENT_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val name = data?.getStringExtra("name")
            val email = data?.getStringExtra("email")
            photo = data?.getStringExtra("photo").toString()
            chooseClient.setText("$name")
            client_email.text = email
        }
    }
}
