package com.utilityhub.csapp.domain.use_case.auth

import com.utilityhub.csapp.domain.model.Response
import com.utilityhub.csapp.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow

class SignInWithGoogle(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(idToken : String): Flow<Response<Boolean>> = repository.firebaseSignInWithGoogle(idToken)
}