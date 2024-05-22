package com.example.all_in_one_fitness.steps

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.all_in_one_fitness.R
import com.example.all_in_one_fitness.data.StepsViewModel
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import java.text.SimpleDateFormat
import java.time.LocalDate


class StepsFragment : Fragment(), SensorEventListener{

    private var sensorManager: SensorManager? = null

    private var running = false
    private var totalSteps = 0f
    private var currentSteps = 0
    private var previousTotalSteps = 0f

    private lateinit var textSteps: TextView
    private lateinit var progressCircularProgressBar: CircularProgressBar
    private lateinit var mStepsViewModel: StepsViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,

        ): View? {
        mStepsViewModel = ViewModelProvider(this)[StepsViewModel::class.java]
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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onSensorChanged(event: SensorEvent?) {
        mStepsViewModel.readAllData.observe(this, Observer { user ->
            val data = user[0].localDate
            val dbSteps = user[0].totalSteps

            totalSteps = event!!.values[0]
            currentSteps = totalSteps.toInt() - dbSteps.toInt()

            if(LocalDate.now().toString() > data)
            {
                mStepsViewModel.updateSteps(LocalDate.now().toString(), (totalSteps-dbSteps), 1)
            }
        })
        textSteps.text = currentSteps.toString()
        progressCircularProgressBar.progress = currentSteps.toFloat()
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }
}