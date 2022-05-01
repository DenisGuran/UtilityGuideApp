package com.utilityhub.csapp.domain.use_case.auth

data class ValidationUseCases(
    val validateEmail: ValidateEmail,
    val validateConfirmedPassword: ValidateConfirmedPassword,
    val validatePassword: ValidatePassword,
    val validateUsername: ValidateUsername
)
