package com.utilityhub.csapp.core.utils

import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.module.AppGlideModule
import com.jsibbold.zoomage.ZoomageView
import com.utilityhub.csapp.R

@GlideModule
class GlideLoader(val context: Context) : AppGlideModule(){

    private val cacheStrategy = DiskCacheStrategy.NONE

    fun loadZoomageView(drawable: Int, zoomView: ZoomageView){
        Glide
            .with(context)
            .load(drawable)
            .dontTransform()
            .diskCacheStrategy(cacheStrategy)
            .into(zoomView)
    }

    fun loadImageView(drawable: Int, imageView: ImageView){
        Glide
            .with(context)
            .load(drawable)
            .dontTransform()
            .diskCacheStrategy(cacheStrategy)
            .into(imageView)
    }

    fun loadImageView(bitmap: Bitmap, imageView: ImageView){
        Glide
            .with(context)
            .load(bitmap)
            .dontTransform()
            .diskCacheStrategy(cacheStrategy)
            .into(imageView)
    }

    fun loadImageView(string: String, imageView: ImageView){
        Glide
            .with(context)
            .load(string)
            .error(R.drawable.mirage_background)
            .placeholder(R.drawable.mirage_background)
            .dontTransform()
            .diskCacheStrategy(cacheStrategy)
            .into(imageView)
    }
}