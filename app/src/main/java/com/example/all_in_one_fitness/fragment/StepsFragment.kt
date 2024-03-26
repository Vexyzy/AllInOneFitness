package com.example.all_in_one_fitness.fragment

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.all_in_one_fitness.MainActivity
import com.example.all_in_one_fitness.R
import com.mikhaellopez.circularprogressbar.CircularProgressBar


class StepsFragment : Fragment() {

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

        button.setOnClickListener{
            MainActivity.currentSteps += 100
        }


        val stepsCounter: TextView = requireActivity().findViewById(R.id.stepsCounter)
        val progressBar: CircularProgressBar = requireActivity().findViewById(R.id.progress_circular)
        Handler(Looper.getMainLooper()).postDelayed({
            if(MainActivity.running){
                val currentSpes = MainActivity.currentSteps
                stepsCounter.text = ("$currentSpes")
                progressBar.apply{
                    setProgressWithAnimation(MainActivity.currentSteps!!.toFloat())
                }
            }
        }, 10)

    }

    override fun onResume() {
        super.onResume()
        val stepsCounter: TextView = requireActivity().findViewById(R.id.stepsCounter)
        val progressBar: CircularProgressBar = requireActivity().findViewById(R.id.progress_circular)
        Handler(Looper.getMainLooper()).postDelayed({
            if(MainActivity.running){
                val currentSpes = MainActivity.currentSteps
                stepsCounter.text = ("$currentSpes")
                progressBar.apply{
                    setProgressWithAnimation(MainActivity.currentSteps!!.toFloat())
                }
            }
        }, 10)

    }
}