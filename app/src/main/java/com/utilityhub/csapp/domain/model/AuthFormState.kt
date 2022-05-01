package com.utilityhub.csapp.domain.model

data class AuthFormState(
    val hasNoError : Boolean,
    val usernameError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val confirmedPasswordError: String? = null
)
