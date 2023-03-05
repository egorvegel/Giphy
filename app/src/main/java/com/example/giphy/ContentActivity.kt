package com.example.giphy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.giphy.databinding.ActivityContentBinding
import com.example.giphy.model.GifEntity

class ContentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityContentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val item = intent.getSerializableExtra("item") as GifEntity
        with(binding){
            type.text = type.text.toString() + " " + item.type
            id.text = id.text.toString() + " " + item.id
            title.text = title.text.toString() + " " + item.title
            rating.text = rating.text.toString() + " " + item.rating
            Glide
                .with(this@ContentActivity)
                .asGif()
                .load(item.url)
                .into(holder)
        }

        binding.back.setOnClickListener {
            finish()
        }
    }
}