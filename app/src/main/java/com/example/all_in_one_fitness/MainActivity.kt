package com.example.all_in_one_fitness

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.all_in_one_fitness.fragment.StepsFragment
import com.example.all_in_one_fitness.timer.TimerFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mikhaellopez.circularprogressbar.CircularProgressBar

class MainActivity : AppCompatActivity(){

    private var sensorManager: SensorManager? = null

    private var running = false
    private var totalSteps = 0f
    private var previousTotalSteps = 0f


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        loadData()
//        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
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

//    override fun onResume() {
//        super.onResume()
//        running = true
//        val stepSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
//
//        if(stepSensor == null){
//            Toast.makeText(this, "No sensor detected on this device", Toast.LENGTH_SHORT).show()
//        } else{
//            sensorManager?.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI)
//        }
//    }

//    override fun onSensorChanged(event: SensorEvent?) {
//        if(running){
//            val stepsCounter: TextView = findViewById(R.id.stepsCounter)
//            val progressBar: CircularProgressBar = findViewById(R.id.progress_circular)
//            totalSteps = event!!.values[0]
//            val currentSteps = totalSteps.toInt() - previousTotalSteps.toInt()
//            stepsCounter.text = ("$currentSteps")
//
//            progressBar.apply{
//                setProgressWithAnimation(currentSteps.toFloat())
//            }
//        }
//    }
//
//    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
//
//    private fun saveData() {
//        val sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
//        val editor = sharedPreferences.edit()
//        editor.putFloat("key1", previousTotalSteps)
//        editor.apply()
//    }
//    private fun loadData() {
//        val sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
//        val savedNumber = sharedPreferences.getFloat("key1", 0f);
//        Log.d("MainActivity", "$savedNumber")
//        previousTotalSteps = savedNumber
//    }
}