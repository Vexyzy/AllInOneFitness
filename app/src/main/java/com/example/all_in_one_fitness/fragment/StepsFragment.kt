package com.example.all_in_one_fitness.fragment

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
import androidx.lifecycle.ViewModelProvider
import com.example.all_in_one_fitness.R
import com.example.all_in_one_fitness.data.Steps
import com.example.all_in_one_fitness.data.StepsViewModel
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import java.time.LocalDate
import java.time.Month
import kotlin.reflect.typeOf


class StepsFragment : Fragment(), SensorEventListener{

    private var sensorManager: SensorManager? = null

    private var running = false
    private var totalSteps = 0f
    private var previousTotalSteps = 0f

    private lateinit var mStepsViewModel: StepsViewModel
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
//        mStepsViewModel = ViewModelProvider(this).get(StepsViewModel::class.java)
        val localDate = LocalDate.now()
        val secondDate = LocalDate.of(2024, Month.MARCH,31)
        println(Month.MARCH.toString())
//        insertToDatabase(0f,2020,  Month.MARCH, 20)
        println(localDate > secondDate)
    }

    override fun onResume() {
        super.onResume()
        running = true
        val stepsSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        if(stepsSensor == null){
            Toast.makeText(requireContext(),
                "No Sensor Manager detected in this device", Toast.LENGTH_SHORT)
                .show()
        } else{
            sensorManager?.registerListener(this, stepsSensor, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        loadData(event)

        textSteps = requireView().findViewById(R.id.text_steps)
        progressCircularProgressBar = requireView().findViewById(R.id.progress_circular)
        val currentSteps = totalSteps.toInt() - previousTotalSteps.toInt()
        textSteps.text = ("$currentSteps")
        progressCircularProgressBar.progress = currentSteps.toFloat()

    }

    private fun loadData(event: SensorEvent?){
        if(running){
            totalSteps = event!!.values[0]
        }
    }

    private fun insertToDatabase(steps: Float, year: Int, month: Month, day: Int){
        //val stepsDB = Steps(0, year, month, day, steps)
        //mStepsViewModel.addSteps(stepsDB)
    }
    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        TODO("Not yet implemented")
    }
}