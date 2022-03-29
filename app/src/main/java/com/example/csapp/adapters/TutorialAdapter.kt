package com.example.csapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.csapp.databinding.TutorialLayoutBinding
import com.example.csapp.models.TutorialData
import com.example.csapp.utils.GlideLoader

class TutorialAdapter(
    private val tutorialList: ArrayList<TutorialData>
) : RecyclerView.Adapter<TutorialAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: TutorialLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TutorialAdapter.ViewHolder {
        val binding =
            TutorialLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TutorialAdapter.ViewHolder, position: Int) {
        holder.apply {
            tutorialList[position].apply {
                binding.apply {
                    textviewAbove.text = step.toString()
                    textviewBelow.text = details
                    GlideLoader(itemView.context).loadZoomageView(image, zoomView)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return tutorialList.size
    }
}


