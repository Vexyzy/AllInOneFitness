package com.example.all_in_one_fitness.fitness

import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.AdapterView
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.all_in_one_fitness.R
import com.example.all_in_one_fitness.data_fitness.Fitness
import com.example.all_in_one_fitness.data_fitness.FitnessViewModel
import org.w3c.dom.Text


class FitnessCardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fitness_card)
        val extras = intent.extras

        val listView = findViewById<ListView>(R.id.list)
        val titleView = findViewById<TextView>(R.id.textView_title)
        val back = findViewById<ImageView>(R.id.back)
        val imgBack = findViewById<ImageView>(R.id.imageView)

        back.setOnClickListener {
            finish()
        }

        when(extras!!.getString("type")){
            "breast" -> {
                val listOfExercise = FitnessUtil.listOfBreastExercise
                val adapter = FitnessListAdapter(this, listOfExercise)
                imgBack.setImageResource(R.drawable.breast)
                listView.adapter = adapter
                titleView.text = "Упражнения на грудь"

                listView.onItemClickListener =
                    AdapterView.OnItemClickListener { parent, view, position, id ->
                        val intent = Intent(this, FitnessExercise::class.java)
                        intent.putExtra("title", FitnessUtil.listOfBreastExercise[position].title)
                        intent.putExtra("subtitle", FitnessUtil.listOfBreastExercise[position].subTitle)
                        intent.putExtra("gif", FitnessUtil.listOfBreastExercise[position].gifLink)
                        startActivity(intent)
                    }
            }
            "arms" -> {
                val listOfExercise = FitnessUtil.listOfArmsExercise
                val adapter = FitnessListAdapter(this, listOfExercise)
                imgBack.setImageResource(R.drawable.arms)
                listView.adapter = adapter
                titleView.text = "Упражнения на руки"

                listView.onItemClickListener =
                    AdapterView.OnItemClickListener { parent, view, position, id ->
                        val intent = Intent(this, FitnessExercise::class.java)
                        intent.putExtra("title", FitnessUtil.listOfArmsExercise[position].title)
                        intent.putExtra("subtitle", FitnessUtil.listOfArmsExercise[position].subTitle)
                        intent.putExtra("gif", FitnessUtil.listOfArmsExercise[position].gifLink)
                        startActivity(intent)
                    }
            }
            "back" -> {
                val listOfExercise = FitnessUtil.listOfBackExercise
                val adapter = FitnessListAdapter(this, listOfExercise)
                imgBack.setImageResource(R.drawable.back)
                listView.adapter = adapter
                titleView.text = "Упражнения на спину"

                listView.onItemClickListener =
                    AdapterView.OnItemClickListener { parent, view, position, id ->
                        val intent = Intent(this, FitnessExercise::class.java)
                        intent.putExtra("title", FitnessUtil.listOfBackExercise[position].title)
                        intent.putExtra("subtitle", FitnessUtil.listOfBackExercise[position].subTitle)
                        intent.putExtra("gif", FitnessUtil.listOfBackExercise[position].gifLink)
                        startActivity(intent)
                    }
            }
            "legs" -> {
                val listOfExercise = FitnessUtil.listOfLegsExercise
                val adapter = FitnessListAdapter(this, listOfExercise)
                imgBack.setImageResource(R.drawable.legs)
                listView.adapter = adapter
                titleView.text = "Упражнения на ноги"

                listView.onItemClickListener =
                    AdapterView.OnItemClickListener { parent, view, position, id ->
                        val intent = Intent(this, FitnessExercise::class.java)
                        intent.putExtra("title", FitnessUtil.listOfLegsExercise[position].title)
                        intent.putExtra("subtitle", FitnessUtil.listOfLegsExercise[position].subTitle)
                        intent.putExtra("gif", FitnessUtil.listOfLegsExercise[position].gifLink)
                        startActivity(intent)
                    }
            }
        }
    }

}