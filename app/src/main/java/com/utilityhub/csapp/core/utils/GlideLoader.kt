package com.utilityhub.csapp.core.utils

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.module.AppGlideModule
import com.jsibbold.zoomage.ZoomageView

@GlideModule
class GlideLoader(val context: Context) : AppGlideModule(){

    fun loadZoomageView(drawable: Int, zoomView: ZoomageView){
        Glide
            .with(context)
            .load(drawable)
            .dontTransform()
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(zoomView)
    }

    fun loadZoomageView(uri: Uri, zoomView: ZoomageView){
        Glide
            .with(context)
            .load(uri)
            .dontTransform()
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(zoomView)
    }

    fun loadImageView(drawable: Int, imageView: ImageView){
        Glide
            .with(context)
            .load(drawable)
            .dontTransform()
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(imageView)
    }

    fun loadImageView(uri: Uri, imageView: ImageView){
        Glide
            .with(context)
            .load(uri)
            .dontTransform()
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(imageView)
    }
}