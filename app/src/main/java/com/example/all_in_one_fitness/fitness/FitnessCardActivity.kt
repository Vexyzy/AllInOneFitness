package com.example.all_in_one_fitness.fitness

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.all_in_one_fitness.R


class FitnessCardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fitness_card)
        val extras = intent.extras

        val title = extras!!.getString("title")
        val type = extras.getString("type")
        val subtitle = extras.getString("subtitle")
        var link: String? = null
        var id = ""
        if(extras.getString("link") != null) {
            link = extras.getString("link")
            if(link!!.startsWith('h')){
                id = link!!.substring(32, 43)
            }
            else {
                id = link!!.substring(24, 35)
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
        if(link != null)
        {
            val url = "https://img.youtube.com/vi/$id/maxresdefault.jpg"
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
    }
}