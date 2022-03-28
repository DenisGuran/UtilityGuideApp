package com.example.csapp.adapters


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.csapp.R
import com.example.csapp.models.Data
import com.example.csapp.utils.GlideLoader
import kotlinx.android.synthetic.main.card_layout.view.*

class UtilityAdapter(private val context:Context, private val lst:ArrayList<Data>, private val onClickListener: OnClickListener): RecyclerView.Adapter<UtilityAdapter.ViewHolder>(){

    interface OnClickListener{
        fun onItemClick(position: Int)
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UtilityAdapter.ViewHolder {
        val view= LayoutInflater.from(parent.context.applicationContext)
            .inflate(R.layout.card_layout ,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: UtilityAdapter.ViewHolder, position: Int) {
        val item = lst[position]
        holder.apply {
            itemView.pos_name.text = item.name
            GlideLoader(context).loadImageView(item.image, itemView.pos_image)
            itemView.setOnClickListener {
                onClickListener.onItemClick(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return lst.size
    }
}


