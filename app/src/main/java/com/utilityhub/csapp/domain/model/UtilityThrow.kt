package com.utilityhub.csapp.domain.model

import android.os.Parcelable
import com.google.firebase.firestore.DocumentId
import kotlinx.parcelize.Parcelize

@Parcelize
data class UtilityThrow(
    @DocumentId
    val id: String? = null,
    var name: String? = null,
    var img: String? = null,
    var tags: MutableList<String>? = null,
    var tutorial: MutableList<Tutorial>? = null
) : Parcelable