package com.utilityhub.csapp.domain.model

import com.utilityhub.csapp.R
import com.utilityhub.csapp.core.UiText

data class ValidationResponse(
    val isValid: Boolean,
    val errorMessage: UiText.StringResource? = UiText.StringResource(R.string.null_string)
)
