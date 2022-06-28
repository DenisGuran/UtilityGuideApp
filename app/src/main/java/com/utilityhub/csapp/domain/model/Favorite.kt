package com.utilityhub.csapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Favorite(
    var landing: String? = null,
    var map: String? = null,
    var throwing: String? = null,
    var utilityType: String? = null
) : Parcelable
