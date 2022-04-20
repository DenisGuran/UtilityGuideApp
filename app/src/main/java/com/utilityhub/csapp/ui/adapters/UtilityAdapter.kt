package com.utilityhub.csapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.utilityhub.csapp.databinding.LayoutUtilityBinding
import com.utilityhub.csapp.domain.model.Utility

class UtilityAdapter(
    private val onUtilityClickListener: OnUtilityClickListener
) : ListAdapter<Utility, UtilityAdapter.UtilityViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UtilityAdapter.UtilityViewHolder {
        val binding =
            LayoutUtilityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UtilityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UtilityViewHolder, position: Int) {
        val utility = currentList[position]
        holder.binding.apply {
            posName.text = utility.name
            posImage.load(utility.img) {
                crossfade(true)
            }
            posCard.setOnClickListener {
                onUtilityClickListener.onUtilityClick(position)
            }
        }
    }

    override fun getItemCount() = currentList.size

    private class DiffCallback : DiffUtil.ItemCallback<Utility>() {
        override fun areItemsTheSame(oldItem: Utility, newItem: Utility) =
            oldItem.name == newItem.name

        override fun areContentsTheSame(oldItem: Utility, newItem: Utility) =
            oldItem == newItem

    }

    inner class UtilityViewHolder(val binding: LayoutUtilityBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface OnUtilityClickListener {
        fun onUtilityClick(position: Int)
    }

}