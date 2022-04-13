package com.utilityhub.csapp.domain.use_case.auth

import com.utilityhub.csapp.domain.repository.AuthRepository

class GetAuthState(
    private val repository: AuthRepository
) {
    operator fun invoke() = repository.getUserAuthState()
}