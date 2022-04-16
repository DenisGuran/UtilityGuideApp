package com.utilityhub.csapp.domain.use_case.auth

import com.utilityhub.csapp.domain.repository.AuthRepository

class AddFirestoreUser(
    private val repository: AuthRepository
) {
    suspend operator fun invoke() = repository.addUserInDatabase()
}