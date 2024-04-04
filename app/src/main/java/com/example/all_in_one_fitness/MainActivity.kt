package com.example.all_in_one_fitness

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.all_in_one_fitness.data.Steps
import com.example.all_in_one_fitness.fitness.FitnessFragment
import com.example.all_in_one_fitness.steps.StepsFragment
import com.example.all_in_one_fitness.timer.TimerFragment
import com.example.roomapp.data.StepsViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity(){

    private lateinit var sharedPref: SharedPreferences
    private lateinit var mStepsViewModel: StepsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_time)

        sharedPref = getSharedPreferences("mypref", Context.MODE_PRIVATE)
        if(sharedPref.getBoolean("firstTime", true)){
            mStepsViewModel = ViewModelProvider(this)[StepsViewModel::class.java]
            insertToDatabase(0f, 2020, "JANUARY", 1)
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
    private fun insertToDatabase(steps: Float, year: Int, month: String, day: Int){
        //CHANGE FROM INSERT TO CHANGE
        val stepsDB = Steps(0, year, month, day, steps)
        mStepsViewModel.addSteps(stepsDB)
    }
}