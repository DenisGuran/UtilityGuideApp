package com.utilityhub.csapp.domain.use_case.auth

import android.util.Patterns
import com.utilityhub.csapp.domain.model.ValidationResponse

class ValidateEmail {

    operator fun invoke(email: String): ValidationResponse {
        if (email.isBlank()) {
            return ValidationResponse(
                isValid = false,
                errorMessage = "The email is required"
            )
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return ValidationResponse(
                isValid = false,
                errorMessage = "Please, provide a valid email"
            )
        }
        return ValidationResponse(
            isValid = true
        )

    }
}