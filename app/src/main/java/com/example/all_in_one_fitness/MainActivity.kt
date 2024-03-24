package com.example.all_in_one_fitness

import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.all_in_one_fitness.fragment.StepsFragment
import com.example.all_in_one_fitness.timer.TimerFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(){

    private var sensorManager: SensorManager? = null

    private var running = false
    private var totalSteps = 0f
    private var previousTotalSteps = 0f


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottomNavigation)
        bottomNavigation.setOnItemSelectedListener {
            when(it.itemId){
                R.id.nav_steps -> {
                    replaceFragment(StepsFragment())
                }
                R.id.nav_timers ->{
                    replaceFragment(TimerFragment())
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