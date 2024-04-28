package com.example.all_in_one_fitness.fitness

import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.all_in_one_fitness.R
import com.example.all_in_one_fitness.data_fitness.Fitness

class FitnessListAdapter(
    private val context: Context,
    private val fitnessList: List<Exercise>
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

        val gif = view.findViewById<ImageView>(R.id.gif_image)
        val title = view.findViewById<TextView>(R.id.title_tv)

        Glide.with(context).asGif().load(fitness.gifLink).into(gif)
        title.text = fitness.title

        return view
    }


}