package com.utilityhub.csapp.domain.use_case.auth

import com.utilityhub.csapp.domain.model.ValidationResponse

class ValidateUsername {

    operator fun invoke(username: String): ValidationResponse {
        if (username.isBlank()) {
            return ValidationResponse(
                isValid = false,
                errorMessage = "The username is required"
            )
        }
        return ValidationResponse(
            isValid = true
        )
    }
}