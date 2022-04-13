package com.utilityhub.csapp.domain.use_case.auth

import com.utilityhub.csapp.domain.repository.AuthRepository

class SignOut(
    private val repository: AuthRepository
) {
    suspend operator fun invoke() = repository.signOut()
}