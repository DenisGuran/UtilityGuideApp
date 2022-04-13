package com.utilityhub.csapp.domain.use_case.auth

import com.utilityhub.csapp.domain.repository.AuthRepository

class ResetPassword(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String) = repository.sendPasswordResetEmail(email)
}