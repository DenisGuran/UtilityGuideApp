package com.example.csapp.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.csapp.DataClasses.Data
import com.example.csapp.R
import kotlinx.android.synthetic.main.card_layout.view.*

class RecyclerAdapter(private val lst:ArrayList<Data>, val onClickListener: OnClickListener ): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>(){

    interface OnClickListener{
        fun onItemClick(position: Int)
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        val view= LayoutInflater.from(parent.context.applicationContext).inflate(R.layout.card_layout ,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        holder.apply {
            itemView.pos_name.text=lst[position].name
            itemView.pos_image.setImageResource(lst[position].image)
        }
        holder.itemView.setOnClickListener {
            onClickListener.onItemClick(position)
        }
    }

    override fun getItemCount(): Int {
        return lst.size
    }
}


