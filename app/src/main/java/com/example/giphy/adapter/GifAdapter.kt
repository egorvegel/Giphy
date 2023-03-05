package com.example.giphy.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.giphy.R
import com.example.giphy.databinding.GifItemBinding
import com.example.giphy.model.GifEntity

class GifAdapter(private val listener: Listener) : RecyclerView.Adapter<GifAdapter.GifHolder>() {
    private val gifsList: MutableList<GifEntity> = ArrayList()

    class GifHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val binding = GifItemBinding.bind(item)
        fun bind(gif: GifEntity, listener: Listener) = with(binding) {
            Glide
                .with(itemView.context)
                .asGif()
                .load(gif.url)
                .into(holder)

            itemView.setOnClickListener {
                listener.onClick(gif)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.gif_item, parent, false)
        return GifHolder(view)
    }

    override fun onBindViewHolder(holder: GifHolder, position: Int) {
        holder.bind(gifsList[position], listener)
    }

    override fun getItemCount(): Int {
        return gifsList.size
    }

    fun setGifsListItems(gifs: MutableList<GifEntity>, isNewWord: Boolean) {
        if (isNewWord) gifsList.clear()
        gifsList.addAll(gifs)
        notifyDataSetChanged()
    }

    interface Listener {
        fun onClick(gif: GifEntity)
    }
}