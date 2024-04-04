package com.example.all_in_one_fitness

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import com.example.all_in_one_fitness.fitness.FitnessFragment
import com.example.all_in_one_fitness.steps.StepsFragment
import com.example.all_in_one_fitness.timer.TimerFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class FirstTimeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottomNavigation)
        replaceFragment(StepsFragment())
        bottomNavigation.selectedItemId = R.id.nav_steps

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
                    replaceFragment(FitnessFragment())
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