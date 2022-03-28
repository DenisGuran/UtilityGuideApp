package com.example.csapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.csapp.R
import com.example.csapp.models.TutorialData
import com.example.csapp.utils.GlideLoader
import kotlinx.android.synthetic.main.tutorial_layout.view.*

class TutorialAdapter(private val context: Context, private val tutorialList:ArrayList<TutorialData>): RecyclerView.Adapter<TutorialAdapter.ViewHolder>(){

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TutorialAdapter.ViewHolder {
        val view= LayoutInflater.from(parent.context.applicationContext)
            .inflate(R.layout.tutorial_layout ,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: TutorialAdapter.ViewHolder, position: Int) {
        val tutorial = tutorialList[position]
        holder.apply {
            itemView.textview_above.text = tutorial.step.toString()
            itemView.textview_below.text = tutorial.details
            GlideLoader(context).loadZoomageView(tutorial.image, itemView.zoomView)
        }
    }

    override fun getItemCount(): Int {
        return tutorialList.size
    }
}


