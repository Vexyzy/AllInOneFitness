package com.example.all_in_one_fitness.fragment

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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.all_in_one_fitness.R
import com.example.all_in_one_fitness.data.MonthConverter
import com.example.all_in_one_fitness.data.Steps
import com.example.roomapp.data.StepsViewModel
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.launch
import java.security.KeyStore.TrustedCertificateEntry
import java.time.LocalDate
import java.time.Month
import kotlin.reflect.typeOf


class StepsFragment : Fragment(), SensorEventListener{

    private var sensorManager: SensorManager? = null

    private var running = false
    private var totalSteps = 0f

    private lateinit var mStepsViewModel: StepsViewModel
    private var listSteps = emptyList<Steps>()
    private var databaseDate: LocalDate? = null
    private lateinit var localDate: LocalDate


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
        sensorManager = requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mStepsViewModel = ViewModelProvider(this).get(StepsViewModel::class.java)
        //insertToDatabase(0f, 2020, "March", 1)
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
        mStepsViewModel.readAllData.observe(viewLifecycleOwner, Observer { steps ->
            setData(steps)
            textSteps = requireView().findViewById(R.id.stepsCounter)
            progressCircularProgressBar = requireView().findViewById(R.id.progress_circular)

            totalSteps = event!!.values[0]
            if(localDate > databaseDate)
            {
                //SET NEW DATE IN DB
                //SET PREV TOTAL STEPS IN DB
                val updateSteps = Steps(1, localDate.year, localDate.month.toString(), localDate.dayOfMonth, totalSteps)
                mStepsViewModel.updateSteps(updateSteps)
            }
            val currentSteps = totalSteps.toInt() - listSteps[0].totalSteps.toInt()
            textSteps.text = ("$currentSteps")
            progressCircularProgressBar.progress = currentSteps.toFloat()
        })
    }

    private fun loadData(event: SensorEvent?){
        if(running){
            totalSteps = event!!.values[0]
        }
    }

    private fun insertToDatabase(steps: Float, year: Int, month: String, day: Int){
        //CHANGE FROM INSERT TO CHANGE
        val stepsDB = Steps(0, year, month, day, steps)
        mStepsViewModel.addSteps(stepsDB)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun setData(steps: List<Steps>){
        listSteps = steps

        databaseDate = LocalDate.of(listSteps[0].year, MonthConverter.toMonth(listSteps[0].month), listSteps[0].day)
        localDate = LocalDate.now()
    }
    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }
}