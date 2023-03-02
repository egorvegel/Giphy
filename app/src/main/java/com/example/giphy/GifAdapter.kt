package com.example.giphy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.giphy.databinding.GifItemBinding
import com.example.giphy.model.GifEntity

class GifAdapter(val listener: Listener) : RecyclerView.Adapter<GifAdapter.PlantHolder>() {
    private val gifsList = ArrayList<GifEntity>()

    class PlantHolder(item: View) : RecyclerView.ViewHolder(item) {

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.gif_item, parent, false)
        return PlantHolder(view)
    }

    override fun onBindViewHolder(holder: PlantHolder, position: Int) {
        holder.bind(gifsList[position], listener)
    }

    override fun getItemCount(): Int {
        return gifsList.size
    }

    fun addGif(gif: GifEntity) {
        gifsList.add(gif)
        notifyDataSetChanged()
    }

    fun setGifsListItems(imagesIds: MutableList<GifEntity>) {
        gifsList.clear()
        gifsList.addAll(imagesIds)
        notifyDataSetChanged()
    }

    interface Listener{
        fun onClick(gif: GifEntity)
    }
}