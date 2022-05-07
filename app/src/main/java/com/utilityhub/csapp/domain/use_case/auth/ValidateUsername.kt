package com.utilityhub.csapp.domain.use_case.auth

import com.utilityhub.csapp.R
import com.utilityhub.csapp.core.UiText
import com.utilityhub.csapp.domain.model.ValidationResponse

class ValidateUsername {

    operator fun invoke(username: String): ValidationResponse {
        if (username.isBlank()) {
            return ValidationResponse(
                isValid = false,
                errorMessage = UiText.StringResource(R.string.required)
            )
        }
        return ValidationResponse(
            isValid = true
        )
    }
}