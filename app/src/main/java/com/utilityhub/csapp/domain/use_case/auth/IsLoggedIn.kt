package com.utilityhub.csapp.domain.use_case.auth

import com.utilityhub.csapp.domain.repository.AuthRepository

class IsLoggedIn(
    private val repository: AuthRepository
) {
    operator fun invoke() = repository.isUserLoggedIn()
}