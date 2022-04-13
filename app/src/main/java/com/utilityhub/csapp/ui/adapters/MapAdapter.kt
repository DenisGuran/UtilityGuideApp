package com.utilityhub.csapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.utilityhub.csapp.core.utils.GlideLoader
import com.utilityhub.csapp.databinding.LayoutCardMapBinding
import com.utilityhub.csapp.domain.model.Map

class MapAdapter(
    private val maps: ArrayList<Map>, private val onClickListener: OnClickListener
) : RecyclerView.Adapter<MapAdapter.MapHolder>(){

    interface OnClickListener {
        fun onItemClick(position: Int)
    }

    inner class MapHolder(binding: LayoutCardMapBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val mapName = binding.tvMapName
        val mapPin = binding.ivPin
        val mapBackground = binding.ivBackground
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MapAdapter.MapHolder {
        val binding =
            LayoutCardMapBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MapHolder(binding)
    }

    override fun onBindViewHolder(holder: MapHolder, position: Int) {
        val model = maps[position]
        holder.apply {
            mapName.text = model.name
            model.pin?.let { GlideLoader(itemView.context).loadImageView(it, mapPin) }
            model.background?.let { GlideLoader(itemView.context).loadImageView(it, mapBackground) }
            itemView.setOnClickListener {
                onClickListener.onItemClick(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return maps.size
    }

}