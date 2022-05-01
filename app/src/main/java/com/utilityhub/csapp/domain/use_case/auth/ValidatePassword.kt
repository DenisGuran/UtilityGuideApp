package com.utilityhub.csapp.domain.use_case.auth

import com.utilityhub.csapp.core.Constants.MIN_PASSWORD_LENGTH
import com.utilityhub.csapp.domain.model.ValidationResponse

class ValidatePassword {

    operator fun invoke(password: String): ValidationResponse {
        if (password.isBlank()) {
            return ValidationResponse(
                isValid = false,
                errorMessage = "The password is required"
            )
        }
        if (password.length < MIN_PASSWORD_LENGTH) {
            return ValidationResponse(
                isValid = false,
                errorMessage = "The password must have at least 6 characters"
            )
        }
        return ValidationResponse(
            isValid = true
        )
    }
}