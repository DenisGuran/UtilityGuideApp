package com.utilityhub.csapp.data.repository

import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.CollectionReference
import com.utilityhub.csapp.core.Constants.ERROR_MESSAGE
import com.utilityhub.csapp.core.Constants.USERS_REF
import com.utilityhub.csapp.domain.model.Response
import com.utilityhub.csapp.domain.model.User
import com.utilityhub.csapp.domain.repository.AuthRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val googleSignInClient: GoogleSignInClient,
    private val auth: FirebaseAuth,
    @Named(USERS_REF) private val usersRef: CollectionReference
) : AuthRepository {

    override suspend fun firebaseSignInWithGoogle(idToken: String): Flow<Response<Boolean>> = flow {
        try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            val authResult = auth.signInWithCredential(credential).await()
            authResult.additionalUserInfo?.apply {
                emit(Response.Success(isNewUser))
            }
        } catch (e: Exception) {
            emit(Response.Failure(e.message ?: ERROR_MESSAGE))
        }
    }

    override suspend fun addUserInDatabase(): Flow<Response<Void>> = flow {
        try {
            auth.currentUser?.apply {
                usersRef.document(uid).set(
                    HashMap<String, Any>()
                )
                    .await().also {
                        emit(Response.Success(it))
                    }
            }
        } catch (e: Exception) {
            emit(Response.Failure(e.message ?: ERROR_MESSAGE))
        }
    }

    override suspend fun signOut(): Flow<Response<Void>> = flow {
        try {
            googleSignInClient.signOut().await().also {
                auth.signOut()
                emit(Response.Success(it))
            }
        } catch (e: Exception) {
            emit(Response.Failure(e.message ?: e.toString()))
        }
    }

    override fun getUserAuthState(): Flow<Boolean> = callbackFlow {
        val authStateListener = FirebaseAuth.AuthStateListener { auth ->
            trySend(auth.currentUser != null)
        }
        auth.addAuthStateListener(authStateListener)
        awaitClose {
            auth.removeAuthStateListener(authStateListener)
        }
    }

    override fun getUserProfile(): Flow<Response<User>> = flow {
        try {
            auth.currentUser?.apply {
                val user = User(email, displayName)
                emit(Response.Success(user))
            }
        } catch (e: Exception) {
            emit(Response.Failure(e.message ?: ERROR_MESSAGE))
        }
    }

    override suspend fun sendPasswordResetEmail(email: String): Flow<Response<Void>> = flow {
        try {
            auth.sendPasswordResetEmail(email).await().also {
                emit(Response.Success(it))
            }
        } catch (e: Exception) {
            emit(Response.Failure(e.message ?: ERROR_MESSAGE))
        }
    }

    override suspend fun firebaseSignInWithEmailAndPassword(
        email: String,
        password: String
    ): Flow<Response<Boolean>> = flow {
        try {
            auth.signInWithEmailAndPassword(email, password).await().also {
                emit(Response.Success(true))
            }
        } catch (e: Exception) {
            emit(Response.Failure(e.message ?: ERROR_MESSAGE))
        }
    }

    override suspend fun register(email: String, password: String, username: String): Flow<Response<Void>> =
        flow {
            try {
                auth.createUserWithEmailAndPassword(email, password).await().also { authResult ->
                    authResult.user!!.updateProfile(userProfileChangeRequest {
                        displayName = username
                    }).await().also {
                        emit(Response.Success(it))
                    }
                }
            } catch (e: Exception) {
                emit(Response.Failure(e.message ?: ERROR_MESSAGE))
            }
        }
}