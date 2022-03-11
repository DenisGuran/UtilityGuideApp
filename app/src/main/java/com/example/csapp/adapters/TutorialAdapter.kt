package com.example.csapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.csapp.models.TutorialData
import com.example.csapp.R
import kotlinx.android.synthetic.main.tutorial_layout.view.*

class TutorialAdapter(private val lst:ArrayList<TutorialData>): RecyclerView.Adapter<TutorialAdapter.ViewHolder>(){

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TutorialAdapter.ViewHolder {
        val view= LayoutInflater.from(parent.context.applicationContext).inflate(R.layout.tutorial_layout ,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: TutorialAdapter.ViewHolder, position: Int) {
        holder.apply {
            itemView.textview_above.text = lst[position].step.toString()
            itemView.myZoomageView.setImageResource(lst[position].image)
            itemView.textview_below.text = lst[position].details
        }
    }

    override fun getItemCount(): Int {
        return lst.size
    }
}


