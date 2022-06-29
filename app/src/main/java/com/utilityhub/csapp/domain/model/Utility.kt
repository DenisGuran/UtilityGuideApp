package com.utilityhub.csapp.domain.model

import com.google.firebase.firestore.DocumentId

data class Utility(
    @DocumentId
    val id: String? = null,
    var name: String? = null,
    var img: String? = null,
    var tags: MutableList<String>? = null
)
