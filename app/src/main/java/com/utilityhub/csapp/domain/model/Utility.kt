package com.utilityhub.csapp.domain.model

data class Utility(
    var name: String? = null,
    var img: String? = null,
    var tags: MutableList<String>? = null
)
