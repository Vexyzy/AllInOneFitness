package com.example.all_in_one_fitness.fitness

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.example.all_in_one_fitness.R


class FitnessFragment : Fragment() {

    private lateinit var cardBreast: CardView
    private lateinit var cardArms: CardView
    private lateinit var cardBack: CardView
    private lateinit var cardLegs: CardView
    private lateinit var cardCardio: CardView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_fitness, container, false)

        cardBreast = view.findViewById(R.id.cardView_breast)
        cardArms = view.findViewById(R.id.cardView_arms)
        cardBack = view.findViewById(R.id.cardView_back)
        cardLegs = view.findViewById(R.id.cardView_legs)

        cardBreast.setOnClickListener{
            breastExercise()
        }

        cardArms.setOnClickListener{
            armsExercise()
        }

        cardBack.setOnClickListener {
            backExercise()
        }

        cardLegs.setOnClickListener {
            legsExercise()
        }


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    private fun breastExercise(){
        val intent = Intent(requireContext(), FitnessCardActivity::class.java)
        intent.putExtra("type", "breast")
        startActivity(intent)
    }

    private fun armsExercise(){
        val intent = Intent(requireContext(), FitnessCardActivity::class.java)
        intent.putExtra("type", "arms")
        startActivity(intent)
    }
    private fun backExercise(){
        val intent = Intent(requireContext(), FitnessCardActivity::class.java)
        intent.putExtra("type", "back")
        startActivity(intent)
    }
    private fun legsExercise(){
        val intent = Intent(requireContext(), FitnessCardActivity::class.java)
        intent.putExtra("type", "legs")
        startActivity(intent)
    }
    private fun cardioExercise(){
        val intent = Intent(requireContext(), FitnessCardActivity::class.java)
        intent.putExtra("type", "cardio")
        startActivity(intent)
    }
}