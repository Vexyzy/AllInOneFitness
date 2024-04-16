package com.example.all_in_one_fitness.fitness

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.utils.widget.ImageFilterButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.all_in_one_fitness.R
import com.example.all_in_one_fitness.data_fitness.Fitness
import com.example.all_in_one_fitness.data_fitness.FitnessViewModel


class FitnessFragment : Fragment() {

    private lateinit var mFitnessViewModel: FitnessViewModel
    private var titleList = arrayListOf<Fitness>()
    private lateinit var listView: ListView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_fitness, container, false)
        mFitnessViewModel = ViewModelProvider(this)[FitnessViewModel::class.java]

        mFitnessViewModel.readAllData.observe(viewLifecycleOwner, Observer {
            fitness ->
            titleList.clear()
            for(i in fitness)
            {
                titleList.add(i)
            }
            val adapter = FitnessListAdapter(requireContext(), titleList)
            listView = view.findViewById(R.id.listOfExercises)

            listView.adapter = adapter
        })
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttonAdd = requireActivity().findViewById<ImageFilterButton>(R.id.buttonAdd)
        listView = requireActivity().findViewById(R.id.listOfExercises)

        listView.onItemClickListener =
            OnItemClickListener { parent, view, position, id ->
                val intent = Intent(requireActivity(), FitnessCardActivity::class.java)
                intent.putExtra("id", id)
                intent.putExtra("title", titleList[position].title)
                intent.putExtra("type", titleList[position].type)
                intent.putExtra("subtitle", titleList[position].subTitle)
                if(titleList[position].link.isNotEmpty())
                    intent.putExtra("link", titleList[position].link)
                startActivity(intent)
            }


        buttonAdd.setOnClickListener{
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.framgeContainer, FitnessAddFragment())
                .commit()
            onDestroy()
        }
    }


}