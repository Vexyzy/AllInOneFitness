package com.example.all_in_one_fitness.fragment

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.all_in_one_fitness.MainActivity
import com.example.all_in_one_fitness.R
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import org.w3c.dom.Text


class StepsFragment : Fragment(), SensorEventListener{


    private var sensorManager: SensorManager? = null
    private lateinit var stepsCounter: TextView
    private  lateinit var progressBar: CircularProgressBar
    private var totalSteps = 0f
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,

    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_steps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val button = requireActivity().findViewById<Button>(R.id.button_add)
        stepsCounter = requireActivity().findViewById(R.id.stepsCounter)
        progressBar = requireActivity().findViewById(R.id.progress_circular)

        sensorManager = requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager

        button.setOnClickListener{
            totalSteps += 100
            onSensorChanged(null)
        }
    }

    override fun onResume() {
        super.onResume()
        val stepsSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        if(stepsSensor == null)
        {
            Toast.makeText(requireContext(),
                "No sensor detected on this device",
                Toast.LENGTH_SHORT).show()
        } else {
            sensorManager?.registerListener(this, stepsSensor, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onSensorChanged(p0: SensorEvent?) {
        totalSteps = p0!!.values[0]
        val currentSteps = totalSteps.toInt()
        stepsCounter.text = ("$currentSteps")

        progressBar.apply{
            setProgressWithAnimation(currentSteps.toFloat())
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }
}