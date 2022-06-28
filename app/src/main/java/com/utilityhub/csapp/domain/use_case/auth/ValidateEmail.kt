package com.utilityhub.csapp.domain.use_case.auth

import android.util.Patterns
import com.utilityhub.csapp.R
import com.utilityhub.csapp.common.UiText
import com.utilityhub.csapp.domain.model.ValidationResponse

class ValidateEmail {

    operator fun invoke(email: String): ValidationResponse {
        if (email.isBlank()) {
            return ValidationResponse(
                isValid = false,
                errorMessage = UiText.StringResource(R.string.required)
            )
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return ValidationResponse(
                isValid = false,
                errorMessage = UiText.StringResource(R.string.invalid_email_error)
            )
        }
        return ValidationResponse(
            isValid = true
        )

    }
}