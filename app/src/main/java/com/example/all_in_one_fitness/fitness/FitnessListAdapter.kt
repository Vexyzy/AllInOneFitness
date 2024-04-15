package com.example.all_in_one_fitness.fitness

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.all_in_one_fitness.R
import com.example.all_in_one_fitness.data_fitness.Fitness

class FitnessListAdapter(
    private val context: Context,
    private val fitnessList: ArrayList<Fitness>
) : BaseAdapter() {


    override fun getCount(): Int {
        return fitnessList.size
    }

    override fun getItem(p0: Int): Any {
        return fitnessList[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val fitness = fitnessList[p0]

        val view = LayoutInflater.from(context).inflate(R.layout.fitness_item, p2, false)

        val id = view.findViewById<TextView>(R.id.tv_id)
        val type = view.findViewById<TextView>(R.id.tv_type)
        val title = view.findViewById<TextView>(R.id.tv_title)

        id.text = (p0 + 1).toString()
        type.text = fitness.type
        title.text = fitness.title

        return view
    }


}