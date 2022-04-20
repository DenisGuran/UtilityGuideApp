package com.utilityhub.csapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.utilityhub.csapp.databinding.LayoutMapBinding
import com.utilityhub.csapp.domain.model.Map

class MapAdapter(private val onMapClickListener: OnMapClickListener) :
    ListAdapter<Map, MapAdapter.MapViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MapAdapter.MapViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = LayoutMapBinding.inflate(layoutInflater, parent, false)
        return MapViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MapViewHolder, position: Int) {
        val map = currentList[position]
        holder.binding.apply {
            tvMapName.text = map.name
            ivBackground.load(map.background) {
                crossfade(enable = true)
            }
            ivPin.load(map.pin) {
                crossfade(enable = true)
            }
            mapCard.setOnClickListener {
                onMapClickListener.onMapClick(position)
            }
        }
    }

    override fun getItemCount() = currentList.size

    private class DiffCallback : DiffUtil.ItemCallback<Map>() {
        override fun areItemsTheSame(oldItem: Map, newItem: Map) =
            oldItem.name == newItem.name

        override fun areContentsTheSame(oldItem: Map, newItem: Map) =
            oldItem == newItem

    }

    inner class MapViewHolder(val binding: LayoutMapBinding) : RecyclerView.ViewHolder(binding.root)

    interface OnMapClickListener {
        fun onMapClick(position: Int)
    }

}