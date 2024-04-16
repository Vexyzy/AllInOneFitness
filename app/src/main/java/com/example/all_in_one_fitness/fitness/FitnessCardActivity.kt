package com.example.all_in_one_fitness.fitness

import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.all_in_one_fitness.R
import com.example.all_in_one_fitness.data_fitness.Fitness
import com.example.all_in_one_fitness.data_fitness.FitnessViewModel


class FitnessCardActivity : AppCompatActivity() {

    private lateinit var mFitnessViewModel: FitnessViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fitness_card)
        val extras = intent.extras

        mFitnessViewModel = ViewModelProvider(this)[FitnessViewModel::class.java]

        val title = extras!!.getString("title")
        val type = extras.getString("type")
        val subtitle = extras.getString("subtitle")
        var link: String? = null
        var id = ""
        if (extras.getString("link") != null) {
            link = extras.getString("link")
            id = if (link!!.startsWith('h')) {
                link.substring(32, 43)
            } else {
                link.substring(24, 35)
            }
        }
//        https://www.youtube.com/watch?v=VwUYKfmXVTU&ab_channel=SJBody
        val textViewTitle = findViewById<TextView>(R.id.textView_title)
        val textViewType = findViewById<TextView>(R.id.textView_type)
        val textViewSubtitle = findViewById<TextView>(R.id.textView_subtitle)
        val imageYoutube = findViewById<ImageView>(R.id.imageView_youtube)
        val buttonBack = findViewById<ImageButton>(R.id.button_back)
        val buttonEdit = findViewById<Button>(R.id.button_edit)
        val buttonRemove = findViewById<Button>(R.id.button_remove)

        textViewTitle.text = title
        textViewType.text = type
        textViewSubtitle.text = subtitle
        if (link != null) {
            val url = "https://img.youtube.com/vi/$id/0.jpg"
            println(url)
            Glide.with(this).load(url).into(imageYoutube)

            imageYoutube.setOnClickListener {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                startActivity(browserIntent)
            }
        }

        buttonBack.setOnClickListener {
            finish()
        }

        buttonEdit.setOnClickListener {
            val intent = Intent(this, FitnessChange::class.java)
            intent.putExtra("id", extras.getLong("id"))
            intent.putExtra("title", title)
            intent.putExtra("type", type)
            intent.putExtra("link", link)
            intent.putExtra("subtitle", subtitle)
            startActivity(intent)
            finish()
        }

        buttonRemove.setOnClickListener {
            val builder1: AlertDialog.Builder = AlertDialog.Builder(this)
            builder1.setMessage("Точно удалить элемент?")
            builder1.setCancelable(true)

            builder1.setPositiveButton(
                "Да",
                DialogInterface.OnClickListener { dialog, id ->
                    dialog.cancel()
                    mFitnessViewModel.deleteFitness(
                        Fitness(
                        extras.getLong("id").toInt() + 1,
                        type.toString(),
                        title.toString(),
                        subtitle.toString(),
                        link.toString()
                        )
                    )
                    finish()
                })

            builder1.setNegativeButton(
                "Нет",
                DialogInterface.OnClickListener { dialog, id -> dialog.cancel() })

            val alert11: AlertDialog = builder1.create()
            alert11.show()
        }
    }
}