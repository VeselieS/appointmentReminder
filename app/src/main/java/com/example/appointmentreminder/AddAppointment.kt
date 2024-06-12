package com.example.appointmentreminder

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText

class AddAppointment : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_appointment)

        val editTime: EditText = findViewById(R.id.editTextTime)
        val editDate: EditText = findViewById(R.id.editTextDate)
        val backButton: Button = findViewById<Button>(R.id.bBack)
        val createButton: Button = findViewById<Button>(R.id.bCreate)
        val timeButton: ImageButton = findViewById<ImageButton>(R.id.imageButtonTime)
        val dateButton: ImageButton = findViewById(R.id.imageButtonDate)
        val chooseClient: TextInputEditText = findViewById<TextInputEditText>(R.id.clientEditText)

        editTime.inputType = InputType.TYPE_NULL
        editDate.inputType = InputType.TYPE_NULL


        backButton.setOnClickListener() {
            finish()
        }

        timeButton.setOnClickListener {
            // Получаем текущее время
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
            // Получаем текущую дату
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
            startActivity(intent)
        }
    }
}