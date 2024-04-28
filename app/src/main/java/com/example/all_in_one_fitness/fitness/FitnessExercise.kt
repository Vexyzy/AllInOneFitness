package com.example.all_in_one_fitness.fitness

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.all_in_one_fitness.R

class FitnessExercise : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fitness_exercise)

        val title = findViewById<TextView>(R.id.textView2)
        val subtitle = findViewById<TextView>(R.id.subtitle)
        val gif = findViewById<ImageView>(R.id.exercise)

        val back = findViewById<ImageView>(R.id.back)

        back.setOnClickListener {
            finish()
        }

        val extras = intent.extras

        title.text = extras!!.getString("title")
        subtitle.text = extras.getString("subtitle")
        Glide.with(this).asGif().load(extras.getString("gif")).into(gif)
    }
}