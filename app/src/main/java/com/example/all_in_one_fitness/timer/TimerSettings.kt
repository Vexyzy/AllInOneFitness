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


        val numberList = arrayListOf<String>("0")
        for(i in 1..60) {
            numberList.add(i.toString())
        }

        val listAdapter = ArrayAdapter<String>(
            this,
            R.layout.spinner_item,
            numberList
        )

        listAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item)

        spinnerMinutes.adapter = listAdapter
        spinnerSeconds.adapter = listAdapter

        spinnerMinutes.setSelection(1)
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
                    minutes = numberList[selectedItemPosition].toInt()
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
                    seconds = numberList[selectedItemPosition].toInt()
                    //toast.show()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        } finally {

        }


        btnApply.setOnClickListener {
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