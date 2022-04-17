package com.utilityhub.csapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.utilityhub.csapp.databinding.LayoutItemTutorialBinding
import com.utilityhub.csapp.domain.model.Tutorial

class TutorialAdapter(
    private val tutorials: ArrayList<Tutorial>
) : RecyclerView.Adapter<TutorialAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: LayoutItemTutorialBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TutorialAdapter.ViewHolder {
        val binding =
            LayoutItemTutorialBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TutorialAdapter.ViewHolder, position: Int) {
        holder.apply {
            tutorials[position].apply {
                binding.apply {
                    textviewAbove.text = step.toString()
                    textviewBelow.text = details
                    zoomView.load(image){
                        crossfade(true)
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return tutorials.size
    }
}


