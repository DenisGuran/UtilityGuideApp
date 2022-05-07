package com.utilityhub.csapp.domain.model

import com.utilityhub.csapp.R
import com.utilityhub.csapp.core.UiText

data class AuthFormState(
    val hasNoError: Boolean,
    val usernameError: UiText.StringResource? = UiText.StringResource(R.string.null_string),
    val emailError: UiText.StringResource? = UiText.StringResource(R.string.null_string),
    val passwordError: UiText.StringResource? = UiText.StringResource(R.string.null_string),
    val confirmedPasswordError: UiText.StringResource? = UiText.StringResource(R.string.null_string)
)
