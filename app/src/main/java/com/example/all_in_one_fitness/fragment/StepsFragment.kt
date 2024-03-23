package com.example.all_in_one_fitness.fragment

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.all_in_one_fitness.MainActivity
import com.example.all_in_one_fitness.R
import com.mikhaellopez.circularprogressbar.CircularProgressBar


class StepsFragment : Fragment(), SensorEventListener {

    private var sensorManager: SensorManager? = null

    private var running = false
    private var totalSteps = 0f
    private var previousTotalSteps = 0f

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,

    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_steps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
        sensorManager = requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val button = requireView().findViewById<Button>(R.id.button_add)

        button.setOnClickListener{
            val sensor: SensorEvent? = null
            onSensorChanged(sensor)
            totalSteps += 100
        }
    }

    override fun onResume() {
        super.onResume()
        running = true
        val stepSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        if(stepSensor == null){
            Toast.makeText(requireContext(), "No sensor detected on this device", Toast.LENGTH_SHORT).show()
        } else{
            sensorManager?.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI)
        }
    }
    override fun onSensorChanged(event: SensorEvent?) {
        if(running){
            val stepsCounter: TextView = requireView().findViewById(R.id.stepsCounter)
            val progressBar: CircularProgressBar = requireView().findViewById(R.id.progress_circular)
//            totalSteps = event!!.values[0]
            val currentSteps = totalSteps.toInt() - previousTotalSteps.toInt()
            stepsCounter.text = ("$currentSteps")

            progressBar.apply{
                setProgressWithAnimation(currentSteps.toFloat())
            }
            saveData()
        }
    }

    private fun loadData() {
        val sharedPreferences = requireActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val savedNumber = sharedPreferences.getFloat("key1", 0f);
        Log.d("StepsFragment", "$savedNumber")
        previousTotalSteps = savedNumber
    }

    private fun saveData() {
        val sharedPreferences = requireActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putFloat("key1", previousTotalSteps)
        editor.apply()
    }
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }


}