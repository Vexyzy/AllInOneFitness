package com.example.all_in_one_fitness

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.all_in_one_fitness.fragment.StepsFragment
import com.example.all_in_one_fitness.timer.TimerFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import java.sql.Time


class MainActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottomNavigation)

        if(TimerFragment.isNewTimeSet)
        {
            replaceFragment(TimerFragment())
            bottomNavigation.selectedItemId = R.id.nav_timers

        }

        bottomNavigation.setOnItemSelectedListener {
            when(it.itemId){
                R.id.nav_steps -> {
                    replaceFragment(StepsFragment())
                }
                R.id.nav_timers ->{
                    replaceFragment(TimerFragment())
                }
                R.id.nav_fitnessCenter ->{
                }
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment)
    {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.framgeContainer, fragment)
            .commit()
    }
}