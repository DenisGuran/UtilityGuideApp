package com.utilityhub.csapp.domain.use_case.auth

import com.utilityhub.csapp.R
import com.utilityhub.csapp.core.UiText
import com.utilityhub.csapp.domain.model.ValidationResponse

class ValidateConfirmedPassword {

    operator fun invoke(password: String, confirmedPassword: String): ValidationResponse {
        if (password != confirmedPassword) {
            return ValidationResponse(
                isValid = false,
                errorMessage = UiText.StringResource(R.string.invalid_confirm_password_error)
            )
        }
        return ValidationResponse(
            isValid = true
        )
    }
}