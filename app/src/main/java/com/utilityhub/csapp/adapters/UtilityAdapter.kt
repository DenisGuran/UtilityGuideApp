package com.utilityhub.csapp.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.utilityhub.csapp.databinding.CardLayoutBinding
import com.utilityhub.csapp.models.Data
import com.utilityhub.csapp.utils.GlideLoader

class UtilityAdapter(
    private val lst: ArrayList<Data>,
    private val onClickListener: OnClickListener
) : RecyclerView.Adapter<UtilityAdapter.ViewHolder>() {

    interface OnClickListener {
        fun onItemClick(position: Int)
    }

    inner class ViewHolder(val binding: CardLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UtilityAdapter.ViewHolder {
        val binding = CardLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UtilityAdapter.ViewHolder, position: Int) {
        holder.apply {
            lst[position].apply {
                binding.apply {
                    posName.text = name
                    GlideLoader(itemView.context).loadImageView(image, posImage)
                    itemView.setOnClickListener {
                        onClickListener.onItemClick(position)
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return lst.size
    }
}


