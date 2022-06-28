package com.utilityhub.csapp.domain.model

data class UserData(
    var favorites: MutableList<Favorite>? = null
)