package com.utilityhub.csapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UtilityThrow(
    var name: String? = null,
    var img: String? = null,
    var tags: MutableList<String>? = null,
    var tutorial: MutableList<Tutorial>? = null
) : Parcelable