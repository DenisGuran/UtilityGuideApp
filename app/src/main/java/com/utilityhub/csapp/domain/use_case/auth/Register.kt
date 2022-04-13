package com.utilityhub.csapp.domain.use_case.auth

import com.utilityhub.csapp.domain.repository.AuthRepository

class Register(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String, username: String) = repository.register(email, password, username)
}