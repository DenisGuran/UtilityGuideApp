package com.utilityhub.csapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.utilityhub.csapp.databinding.LayoutItemTutorialBinding
import com.utilityhub.csapp.domain.model.Tutorial
import com.utilityhub.csapp.core.utils.GlideLoader

class TutorialAdapter(
    private val tutorialList: ArrayList<Tutorial>
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


