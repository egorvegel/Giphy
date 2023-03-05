package com.example.giphy

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.giphy.adapter.GifAdapter
import com.example.giphy.api.RetrofitService
import com.example.giphy.databinding.ActivityMainBinding
import com.example.giphy.model.GifEntity
import com.example.giphy.repository.MainRepository
import com.example.giphy.viewmodel.MainViewModel
import com.example.giphy.viewmodel.MyViewModelFactory


class MainActivity : AppCompatActivity(), GifAdapter.Listener {
    lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainRepository: MainRepository
    private val adapter = GifAdapter(this)


    private var offset = 0
    private var query = ""
    private var isNewWord: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val retrofitService = RetrofitService.getInstance()
        mainRepository = MainRepository(retrofitService)

        binding.gifsList.layoutManager = GridLayoutManager(this@MainActivity, 2)
        binding.gifsList.adapter = adapter

        binding.search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                query = s.toString()
                isNewWord = true
                offset = 0
                loadGifs()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                query = s.toString()
                isNewWord = true
                offset = 0
                loadGifs()
            }
        })

        binding.gifsList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    isNewWord = false
                    offset += 25
                    loadGifs()
                    Toast.makeText(this@MainActivity, "Last", Toast.LENGTH_SHORT).show()
                    Log.d("offset", offset.toString())
                }
            }
        })
    }

    private fun loadGifs() {
        viewModel = ViewModelProvider(
            this, MyViewModelFactory(mainRepository)
        )[MainViewModel::class.java]
        viewModel.gifData.observe(this) {
            val gifs = mutableListOf<GifEntity>()
            for (i in 0 until it.data.size) {
                gifs.add(
                    GifEntity(
                        it.data[i].type,
                        it.data[i].id,
                        it.data[i].title,
                        it.data[i].rating,
                        it.data[i].images.fixed_height.url
                    )
                )
            }
            adapter.setGifsListItems(gifs, isNewWord)
        }
        viewModel.errorMessage.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
        viewModel.loading.observe(this) {
            if (it) {
                binding.progressbar.visibility = View.VISIBLE
            } else {
                binding.progressbar.visibility = View.GONE
            }
        }
        viewModel.getGifs(query, offset)
    }

    override fun onClick(gif: GifEntity) {
        val intent = Intent(this, ContentActivity::class.java).apply {
            putExtra("item", gif)
        }
        startActivity(intent)
    }
}