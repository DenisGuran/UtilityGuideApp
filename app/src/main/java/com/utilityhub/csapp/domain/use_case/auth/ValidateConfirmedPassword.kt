package com.utilityhub.csapp.domain.use_case.auth

import com.utilityhub.csapp.domain.model.ValidationResponse

class ValidateConfirmedPassword {

    operator fun invoke(password: String, confirmedPassword: String): ValidationResponse {
        if (password != confirmedPassword) {
            return ValidationResponse(
                isValid = false,
                errorMessage = "Passwords do not match"
            )
        }
        return ValidationResponse(
            isValid = true
        )
    }
}