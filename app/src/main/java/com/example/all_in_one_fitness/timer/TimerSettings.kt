package com.example.all_in_one_fitness.timer

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.example.all_in_one_fitness.MainActivity
import com.example.all_in_one_fitness.R
import com.shawnlin.numberpicker.NumberPicker

class TimerSettings : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer_settings)

        val spinnerMinutes: NumberPicker = findViewById(R.id.numberPicker_1)
        val spinnerSeconds: NumberPicker = findViewById(R.id.numberPicker_2)
        val btnApply: AppCompatButton = findViewById(R.id.apply)
        var minutes = 1
        var seconds = 0

        //spinnerMinutes.dividerColor = ContextCompat.getColor(this, R.color.light_green)

        btnApply.setOnClickListener {
            minutes = spinnerMinutes.value
            seconds = spinnerSeconds.value

            if(minutes * 60 + seconds == 0)
            {
                Toast.makeText(
                    this,
                    "Таймер не может начать отсчет с 0 секунд.",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else
            {
                TimerFragment.timerLenInt = minutes * 60 + seconds
                TimerFragment.isNewTimeSet = true
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}