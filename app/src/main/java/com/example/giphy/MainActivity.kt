package com.example.giphy

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.giphy.databinding.ActivityMainBinding
import com.example.giphy.model.GifEntity
import com.example.giphy.model.TestGifEntity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), GifAdapter.Listener {
    private lateinit var binding: ActivityMainBinding
    private val adapter = GifAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.load.setOnClickListener {}
        binding.gifsList.layoutManager = GridLayoutManager (this@MainActivity, 2)
        binding.gifsList.adapter = adapter

        binding.search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                loadGifs(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                loadGifs(s.toString())
            }
        })
    }

    private fun loadGifs(query: String) {
        val apiInterface = ApiInterface.create().getGifs(query)
        apiInterface.enqueue( object : Callback<TestGifEntity> {
            override fun onResponse(call: Call<TestGifEntity>, response: Response<TestGifEntity>) {
                if(response.body() != null){
                    val data = response.body()!!.data
                    val imagesIds = mutableListOf<GifEntity>()
                    for (element in data){
                        imagesIds.add(
                            GifEntity(
                                element.type,
                                element.id,
                                element.title,
                                element.rating,
                                element.images.fixed_height.url
                            ))
                    }
                    adapter.setGifsListItems(imagesIds)
                }
            }

            override fun onFailure(call: Call<TestGifEntity>, t: Throwable) {
            }
        })

    }

    override fun onClick(gif: GifEntity) {
        val intent = Intent(this, ContentActivity::class.java).apply {
            putExtra("item", gif)
        }
        startActivity(intent)
    }
}