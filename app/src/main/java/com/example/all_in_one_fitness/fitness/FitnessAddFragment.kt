package com.example.all_in_one_fitness.fitness

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.all_in_one_fitness.R
import com.example.all_in_one_fitness.data_fitness.Fitness
import com.example.all_in_one_fitness.data_fitness.FitnessViewModel


class FitnessAddFragment : Fragment() {

    private lateinit var spinnerType: Spinner
    private val listOfTypes = arrayListOf<String>("Arms", "Back", "Legs", "Breast")
    private lateinit var type: String

    private lateinit var mFitnessModel: FitnessViewModel
    private lateinit var etTitle: EditText
    private lateinit var etSubtitle: EditText
    private lateinit var etLink: EditText

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_fitness_add, container, false)
        mFitnessModel = ViewModelProvider(this)[FitnessViewModel::class.java]

        spinnerType = view.findViewById<Spinner>(R.id.spinner_types)

        val listAdapter = ArrayAdapter<String>(
            requireContext(),
            R.layout.spinner_item,
            listOfTypes
        )

        listAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item)

        spinnerType.adapter = listAdapter

        etTitle = view.findViewById(R.id.editText_title)
        etSubtitle = view.findViewById(R.id.editText_subtitle)
        etLink = view.findViewById(R.id.editText_link)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttonBack = requireActivity().findViewById<ImageButton>(R.id.btn_back)

        try {
            spinnerType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    itemSelected: View, selectedItemPosition: Int, selectedId: Long,
                ) {
                    type = listOfTypes[selectedItemPosition]
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }finally { }

        val buttonAdd = requireActivity().findViewById<Button>(R.id.btn_add)

        buttonAdd.setOnClickListener {
            insertToDatabase()
        }

        buttonBack.setOnClickListener{
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.framgeContainer, FitnessFragment())
                .commit()
            onDestroy()
        }
    }

    private fun insertToDatabase(){
        val title = etTitle.text.toString()
        val subtitle = etSubtitle.text.toString()
        val link = etLink.text.toString()
        if(inputCheck(title)){
            val fitness = Fitness(0, type, title, subtitle, link)
            mFitnessModel.addFitness(fitness)
            Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_SHORT).show()
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.framgeContainer, FitnessFragment())
                .commit()
            onDestroy()
        }
        else{
            Toast.makeText(requireContext(), "Please fill out title fields.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputCheck(title: String): Boolean {
        return !(TextUtils.isEmpty(title))
    }
}