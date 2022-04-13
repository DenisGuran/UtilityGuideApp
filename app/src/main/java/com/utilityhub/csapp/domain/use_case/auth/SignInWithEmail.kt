package com.utilityhub.csapp.domain.use_case.auth

import com.utilityhub.csapp.domain.repository.AuthRepository

class SignInWithEmail(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String) = repository.firebaseSignInWithEmailAndPassword(email, password)
}