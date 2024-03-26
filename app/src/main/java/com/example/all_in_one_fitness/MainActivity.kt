package com.example.all_in_one_fitness

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.AsyncTask
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.all_in_one_fitness.fragment.StepsFragment
import com.example.all_in_one_fitness.timer.TimerFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mikhaellopez.circularprogressbar.CircularProgressBar


class MainActivity : AppCompatActivity(), SensorEventListener{

    private var sensorManager: SensorManager? = null

    private var running = false
    private var totalSteps = 0f
    private var previousTotalSteps = 0f

    companion object{
        var totalSteps = 0f;
        var running = true
        var currentSteps = 0;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sensorManager = this.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        AsyncTask.execute {
            //TODO your background code
        }

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

    override fun onResume() {
        super.onResume()
        running = true
        val stepSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

//        if(stepSensor == null){
//            Toast.makeText(requireContext(), "No sensor detected on this device", Toast.LENGTH_SHORT).show()
//        } else{
//            sensorManager?.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI)
//        }
    }

    private fun replaceFragment(fragment: Fragment)
    {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.framgeContainer, fragment)
            .commit()
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if(running){
            totalSteps = event!!.values[0]
            currentSteps = totalSteps.toInt()
        }
    }
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    override fun onPause() {
        super.onPause()
        running = true
        sensorManager?.unregisterListener(this)
    }

}