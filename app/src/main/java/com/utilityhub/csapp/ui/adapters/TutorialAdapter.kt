package com.utilityhub.csapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.utilityhub.csapp.databinding.LayoutTutorialBinding
import com.utilityhub.csapp.domain.model.Tutorial

class TutorialAdapter : ListAdapter<Tutorial, TutorialAdapter.TutorialViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TutorialAdapter.TutorialViewHolder {
        val binding =
            LayoutTutorialBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TutorialViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TutorialAdapter.TutorialViewHolder, position: Int) {
        val tutorial = currentList[position]
        holder.binding.apply {
            (position + 1).toString().also { textviewAbove.text = it }
            tutorial.apply {
                textviewBelow.text = details
                zoomView.load(img) {
                    crossfade(true)
                }
            }
        }
    }

    override fun getItemCount() = currentList.size

    private class DiffCallback : DiffUtil.ItemCallback<Tutorial>() {
        override fun areItemsTheSame(oldItem: Tutorial, newItem: Tutorial) =
            oldItem.img == newItem.img

        override fun areContentsTheSame(oldItem: Tutorial, newItem: Tutorial) =
            oldItem == newItem

    }

    inner class TutorialViewHolder(val binding: LayoutTutorialBinding) :
        RecyclerView.ViewHolder(binding.root)
}