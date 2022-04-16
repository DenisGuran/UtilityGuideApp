package com.utilityhub.csapp.domain.use_case.auth

import com.utilityhub.csapp.domain.repository.AuthRepository

class SignInWithGoogle(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(idToken : String) = repository.firebaseSignInWithGoogle(idToken)
}