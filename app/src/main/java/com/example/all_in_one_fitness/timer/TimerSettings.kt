package com.example.all_in_one_fitness.timer

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.all_in_one_fitness.MainActivity
import com.example.all_in_one_fitness.R
import kotlin.math.min


class TimerSettings : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer_settings)

        val spinnerMinutes: Spinner = findViewById(R.id.spinner_minutes)
        val spinnerSeconds: Spinner = findViewById(R.id.spinner_seconds)
        val btnApply: Button = findViewById(R.id.apply)
        var minutes = 1
        var seconds = 0


        val numberMinutesList = arrayListOf<String>("1")
        val numberSecondsList = arrayListOf<String>("0")
        for(i in 1..60)
        {
            numberMinutesList.add(i.toString())
            numberSecondsList.add(i.toString())
        }

        numberMinutesList.removeAt(0)

        val listMinutesAdapter = ArrayAdapter<String>(
            this,
            R.layout.spinner_item,
            numberMinutesList,
        )
        val listSecondsAdapter = ArrayAdapter<String>(
            this,
            R.layout.spinner_item,
            numberSecondsList,
        )

        listMinutesAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        listSecondsAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item)

        spinnerMinutes.adapter = listMinutesAdapter
        spinnerSeconds.adapter = listSecondsAdapter
        try {
            spinnerMinutes.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    itemSelected: View, selectedItemPosition: Int, selectedId: Long,
                ) {
                    //                val toast = Toast.makeText(
                    //                    applicationContext,
                    //                    "Ваш выбор: " + numberList[selectedItemPosition], Toast.LENGTH_SHORT
                    //                )
                    minutes = numberMinutesList[selectedItemPosition].toInt()
                    //toast.show()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }finally {

        }
        try {
            spinnerSeconds.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    itemSelected: View, selectedItemPosition: Int, selectedId: Long,
                ) {
//                val toast = Toast.makeText(
//                    applicationContext,
//                    "Ваш выбор: " + numberList[selectedItemPosition], Toast.LENGTH_SHORT
//                )
                    seconds = numberSecondsList[selectedItemPosition].toInt()
                    //toast.show()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        } finally {

        }


        btnApply.setOnClickListener {
            TimerFragment.timerLenInt = minutes * 60 + seconds
            TimerFragment.isNewTimeSet = true
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}