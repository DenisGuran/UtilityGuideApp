package com.example.csapp.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.jsibbold.zoomage.ZoomageView

class GlideLoader(val context: Context) {

    fun setZoomageView(drawable: Int, zoomView: ZoomageView){
        Glide
            .with(context)
            .load(drawable)
            .dontTransform()
            .into(zoomView)
    }

    fun setImageView(drawable: Int, imageView: ImageView){
        Glide
            .with(context)
            .load(drawable)
            .dontTransform()
            .into(imageView)
    }
}