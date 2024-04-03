package com.example.all_in_one_fitness.steps

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.all_in_one_fitness.R
import com.mikhaellopez.circularprogressbar.CircularProgressBar




class StepsFragment : Fragment(), SensorEventListener{

    private var sensorManager: SensorManager? = null

    private var running = false
    private var totalSteps = 0f
    private var previousTotalSteps = 0

    private lateinit var textSteps: TextView
    private lateinit var progressCircularProgressBar: CircularProgressBar
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,

        ): View? {
        return inflater.inflate(R.layout.fragment_steps, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textSteps = requireActivity().findViewById(R.id.stepsCounter)
        progressCircularProgressBar = requireView().findViewById(R.id.progress_circular)
        sensorManager = requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    override fun onResume() {
        super.onResume()
        running = true
        val stepsSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        sensorManager = requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        if(stepsSensor == null){
            Toast.makeText(requireContext(),
                "No Sensor Manager detected in this device", Toast.LENGTH_SHORT)
                .show()
        } else{
            sensorManager?.registerListener(this, stepsSensor, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if(running) {
            totalSteps = event!!.values[0]
            val currentSteps = totalSteps.toInt() - previousTotalSteps.toInt()
            textSteps.text = currentSteps.toString()
            progressCircularProgressBar.progress = currentSteps.toFloat()
        }
    }

    private fun loadData(event: SensorEvent?){
        if(running){
            totalSteps = event!!.values[0]
            val currentSteps = totalSteps.toInt() - previousTotalSteps.toInt()
            textSteps.text = currentSteps.toString()
            progressCircularProgressBar.progress = currentSteps.toFloat()
        }
    }
    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }
}