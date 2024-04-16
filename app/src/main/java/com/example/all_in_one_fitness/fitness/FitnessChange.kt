package com.example.all_in_one_fitness.fitness

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.all_in_one_fitness.FirstTimeActivity
import com.example.all_in_one_fitness.MainActivity
import com.example.all_in_one_fitness.R
import com.example.all_in_one_fitness.data_fitness.Fitness
import com.example.all_in_one_fitness.data_fitness.FitnessViewModel

class FitnessChange : AppCompatActivity() {

    private lateinit var spinnerType: Spinner
    private val listOfTypes = arrayListOf<String>("Arms", "Back", "Legs", "Breast")
    private lateinit var mFitnessModel: FitnessViewModel

    private lateinit var edTitle: EditText
    private lateinit var edSubtitle: EditText
    private lateinit var edLink: EditText

    private lateinit var buttonDone: Button
    private lateinit var buttonBack: ImageButton

    private var id: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fitness_change)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        val extras = intent.extras

        id = extras!!.getLong("id")
        spinnerType = findViewById<Spinner>(R.id.textView_type)

        val listAdapter = ArrayAdapter<String>(
            this,
            R.layout.spinner_item,
            listOfTypes
        )

        listAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item)

        spinnerType.adapter = listAdapter

        when(extras!!.getString("type")){
            "Arms" ->{
                spinnerType.setSelection(0)
            }
            "Back" ->{
                spinnerType.setSelection(1)
            }
            "Legs" ->{
                spinnerType.setSelection(2)
            }
            "Breast" ->{
                spinnerType.setSelection(3)
            }
        }

        edTitle = findViewById(R.id.textView_title)
        edSubtitle = findViewById(R.id.textView_subtitle)
        edLink = findViewById(R.id.editText)

        edTitle.setText(extras.getString("title"))
        edSubtitle.setText(extras.getString("subtitle"))
        edLink.setText(extras.getString("link"))


        mFitnessModel = ViewModelProvider(this)[FitnessViewModel::class.java]

        buttonDone = findViewById(R.id.button_done)
        buttonBack = findViewById(R.id.button_back)

        buttonBack.setOnClickListener {

            finish()
        }

        buttonDone.setOnClickListener {
            changeElInDatabase()
            finish()
        }
    }

    private fun changeElInDatabase(){
        val title = edTitle.text.toString()
        val subtitle = edSubtitle.text.toString()
        val link = edLink.text.toString()
        val type = spinnerType.selectedItem.toString()

        if(inputCheck(title)){
            val fitness = Fitness(id.toInt()+1, type, title, subtitle, link)
            println(id)
            println(subtitle)
            println(title)
            println(type)
            mFitnessModel.updateFitness(fitness)
            Toast.makeText(this, "Successfully changed!", Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(this, "Please fill out title fields.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputCheck(title: String): Boolean {
        return !(TextUtils.isEmpty(title))
    }
}