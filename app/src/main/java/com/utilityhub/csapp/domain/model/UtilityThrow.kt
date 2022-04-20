package com.utilityhub.csapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class UtilityThrow(
    var name: String? = null,
    var img: String? = null,
    var tutorial: @RawValue MutableList<Tutorial>? = null
) : Parcelable