package com.utilityhub.csapp.domain.model

import android.graphics.Bitmap

data class Map(
    var name: String? = null,
    var pin: Bitmap? = null,
    var background: Int? = null
)