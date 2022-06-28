package com.utilityhub.csapp.domain.use_case.auth

import com.utilityhub.csapp.R
import com.utilityhub.csapp.common.Constants.MIN_PASSWORD_LENGTH
import com.utilityhub.csapp.common.UiText
import com.utilityhub.csapp.domain.model.ValidationResponse

class ValidatePassword {

    operator fun invoke(password: String): ValidationResponse {
        if (password.isBlank()) {
            return ValidationResponse(
                isValid = false,
                errorMessage = UiText.StringResource(R.string.required)
            )
        }
        if (password.length < MIN_PASSWORD_LENGTH) {
            return ValidationResponse(
                isValid = false,
                errorMessage = UiText.StringResource(
                    R.string.min_length_password_error,
                    MIN_PASSWORD_LENGTH
                )
            )
        }
        return ValidationResponse(
            isValid = true
        )
    }
}