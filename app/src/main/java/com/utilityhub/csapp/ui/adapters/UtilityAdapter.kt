package com.utilityhub.csapp.ui.adapters


import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.utilityhub.csapp.core.utils.GlideLoader
import com.utilityhub.csapp.databinding.LayoutCardUtilityBinding
import com.utilityhub.csapp.domain.model.Utility

class UtilityAdapter(
    private val utilities: ArrayList<Utility>, private val onClickListener: OnClickListener
) : RecyclerView.Adapter<UtilityAdapter.UtilityHolder>(), Filterable {

    interface OnClickListener {
        fun onItemClick(position: Int)
    }

    inner class UtilityHolder(binding: LayoutCardUtilityBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val utilityName = binding.posName
        val utilityImg = binding.posImage
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UtilityAdapter.UtilityHolder {
        val binding =
            LayoutCardUtilityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UtilityHolder(binding)
    }

    override fun onBindViewHolder(holder: UtilityHolder, position: Int) {
        val model = utilities[position]
        holder.apply {
            utilityName.text = model.name
            model.img?.let { GlideLoader(itemView.context).loadImageView(it, utilityImg) }
            itemView.setOnClickListener {
                onClickListener.onItemClick(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return utilities.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                TODO("Not yet implemented")
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                TODO("Not yet implemented")
            }

        }
    }

}


