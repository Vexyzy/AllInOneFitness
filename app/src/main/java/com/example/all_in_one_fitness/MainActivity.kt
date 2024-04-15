package com.example.all_in_one_fitness

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.lifecycle.ViewModelProvider
import com.example.all_in_one_fitness.data.Steps
import com.example.all_in_one_fitness.data.StepsViewModel
import java.time.LocalDate
import java.time.Month


class MainActivity : AppCompatActivity(){

    private lateinit var sharedPref: SharedPreferences
    private lateinit var mStepsViewModel: StepsViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_time)

        sharedPref = getSharedPreferences("mypref", Context.MODE_PRIVATE)
        if(sharedPref.getBoolean("firstTime", true)){
            mStepsViewModel = ViewModelProvider(this)[StepsViewModel::class.java]
            insertToDatabase(0f, LocalDate.of(2020, Month.JANUARY, 1))
            sharedPref.edit{
                putBoolean("firstTime", false)
            }
        }
        else{
            val intent = Intent(this, FirstTimeActivity::class.java)
            startActivity(intent)
            finish()
        }

        val btn = findViewById<Button>(R.id.start)
        btn.setOnClickListener {
            val intent = Intent(this, FirstTimeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    private fun insertToDatabase(steps: Float, date: LocalDate){
        //CHANGE FROM INSERT TO CHANGE
        val stepsDB = Steps(0, date.toString(), steps)
        mStepsViewModel.addSteps(stepsDB)
    }
}