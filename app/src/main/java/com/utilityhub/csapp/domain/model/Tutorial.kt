package com.utilityhub.csapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Tutorial(
    var details: String? = null,
    var img: String? = null
) : Parcelable
