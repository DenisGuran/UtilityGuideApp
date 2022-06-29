package com.utilityhub.csapp.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.utilityhub.csapp.databinding.LayoutUtilityBinding
import com.utilityhub.csapp.domain.model.Utility

class UtilityAdapter(
    private val onUtilityClickListener: OnUtilityClickListener
) : ListAdapter<Utility, UtilityAdapter.UtilityViewHolder>(DiffCallback), Filterable {

    private var mainUtilityList = arrayListOf<Utility>()
    private var sortedUtilityList = arrayListOf<Utility>()

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
        holder.bind(utility)
    }

    override fun getItemCount() = currentList.size

    fun setData(list: ArrayList<Utility>?) {
        this.mainUtilityList = list!!
        sortedUtilityList = mainUtilityList
        submitList(list)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Utility>() {
        override fun areItemsTheSame(oldItem: Utility, newItem: Utility) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Utility, newItem: Utility) =
            oldItem == newItem

    }

    inner class UtilityViewHolder(private val binding: LayoutUtilityBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.posCard.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val selectedUtility = currentList[position]
                    onUtilityClickListener.onUtilityClick(selectedUtility)
                }
            }
        }

        fun bind(utility: Utility) {
            binding.apply {
                posName.text = utility.name
                posImage.load(utility.img) {
                    crossfade(true)
                }
            }
        }
    }

    interface OnUtilityClickListener {
        fun onUtilityClick(utility: Utility)
    }

    override fun getFilter(): Filter =
        object : Filter() {
            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                Log.i("C", charSequence.toString())
                val filteredList = arrayListOf<Utility>()
                if (charSequence == null || charSequence.isEmpty()) {
                    filteredList.addAll(mainUtilityList)
                } else {
                    mainUtilityList.forEach { item ->
                        if (item in sortedUtilityList) {
                            if (item.tags?.contains(charSequence.toString()) == true) {
                                filteredList.add(item)
                            } else if (item.name?.lowercase()
                                    ?.contains(charSequence.toString()) == true
                            ) {
                                filteredList.add(item)
                            }
                        }
                    }
                }
                val results = FilterResults()
                results.values = filteredList
                results.count = filteredList.size

                sortedUtilityList = filteredList
                return results
            }

            override fun publishResults(
                charSequence: CharSequence?,
                filterResults: FilterResults?
            ) {
                submitList(filterResults?.values as ArrayList<Utility>)
            }

        }

}