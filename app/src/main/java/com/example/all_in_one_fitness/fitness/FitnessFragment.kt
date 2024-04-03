package com.example.all_in_one_fitness.fitness

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.all_in_one_fitness.R
import com.example.all_in_one_fitness.data.MonthConverter
import com.example.all_in_one_fitness.data.Steps
import com.example.all_in_one_fitness.data_fitness.Fitness
import com.example.all_in_one_fitness.data_fitness.FitnessViewModel
import com.example.roomapp.data.StepsViewModel
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import java.time.LocalDate



class FitnessFragment : Fragment(){

    private lateinit var buttonArms: Button
    private lateinit var buttonBreast: Button
    private lateinit var buttonBeak: Button
    private lateinit var buttonLegs: Button

    private lateinit var mFitnessViewModel: FitnessViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,

        ): View? {
        return inflater.inflate(R.layout.fragment_fitness, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonArms = requireView().findViewById(R.id.buttonArms)
        buttonBreast = requireView().findViewById(R.id.buttonBreast)
        buttonBeak = requireView().findViewById(R.id.buttonBack)
        buttonLegs = requireView().findViewById(R.id.buttonLegs)

        mFitnessViewModel = ViewModelProvider(this)[FitnessViewModel::class.java]

        buttonArms.setOnClickListener {

        }
    }

    override fun onResume() {
        super.onResume()

    }

    private fun insertToDatabase(){
        val fitnessDB = Fitness(
            0,
            "Руки",
            "Стандартые сокращение с гантелями",
            "Делаем так",
            "https://google.com"
        )
        mFitnessViewModel.addFitness(fitnessDB)
    }
}