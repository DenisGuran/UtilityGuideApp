package com.utilityhub.csapp.domain.repository

import com.utilityhub.csapp.domain.model.Response
import com.utilityhub.csapp.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun firebaseSignInWithGoogle(idToken: String): Flow<Response<Boolean>>

    suspend fun firebaseSignInWithEmailAndPassword(email: String, password: String): Flow<Response<Boolean>>

    suspend fun addUserInDatabase(): Flow<Response<Boolean>>

    suspend fun signOut(): Flow<Response<Boolean>>

    suspend fun sendPasswordResetEmail(email: String): Flow<Response<Boolean>>

    suspend fun register(email: String, password: String, username: String): Flow<Response<Boolean>>

    fun getUserAuthState(): Flow<Boolean>

    fun getUserProfile(): Flow<Response<User>>
}