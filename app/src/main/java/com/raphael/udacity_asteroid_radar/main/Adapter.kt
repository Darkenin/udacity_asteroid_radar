package com.raphael.udacity_asteroid_radar.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.raphael.udacity_asteroid_radar.databinding.AsteroidItemBinding
import com.raphael.udacity_asteroid_radar.util.Asteroid

class Adapter (private val clickListener: AsteroidListener) :
    ListAdapter<Asteroid, Adapter.AsteroidViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<Asteroid>() {
        override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem.id == newItem.id
        }

    }

    class AsteroidViewHolder(var binding: AsteroidItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(asteroid: Asteroid) {
            binding.asteroid = asteroid
            binding.executePendingBindings()
        }
    }

    class AsteroidListener(val clickListener: (asteroid: Asteroid) -> Unit) {
        fun onClick(asteroid: Asteroid) = clickListener(asteroid)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {
        val withDataBinding: AsteroidItemBinding = AsteroidItemBinding.inflate(LayoutInflater.from(parent.context))
        return AsteroidViewHolder(withDataBinding)
    }


    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        val asteroid = getItem(position)

        holder.also {
            it.itemView.setOnClickListener{
                clickListener.onClick(asteroid)
            }
            it.bind(asteroid)
        }
    }
}