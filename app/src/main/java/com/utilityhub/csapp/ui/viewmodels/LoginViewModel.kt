package com.utilityhub.csapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.utilityhub.csapp.domain.model.AuthFormState
import com.utilityhub.csapp.domain.use_case.auth.AuthUseCases
import com.utilityhub.csapp.domain.use_case.auth.ValidationUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authUseCases: AuthUseCases,
    private val validationUseCases: ValidationUseCases
) : ViewModel() {

    val authState =
        authUseCases.getAuthState().asLiveData(Dispatchers.IO + viewModelScope.coroutineContext)

    fun firebaseSignInWithEmail(email: String, password: String) =
        liveData(Dispatchers.IO + viewModelScope.coroutineContext) {
            authUseCases.signInWithEmail(email, password).collect { response ->
                emit(response)
            }
        }

    fun firebaseSignInWithGoogle(idToken: String) =
        liveData(Dispatchers.IO + viewModelScope.coroutineContext) {
            authUseCases.signInWithGoogle(idToken).collect { response ->
                emit(response)
            }
        }

    fun addUserToFirestore() = liveData(Dispatchers.IO + viewModelScope.coroutineContext) {
        authUseCases.addFirestoreUser().collect { response ->
            emit(response)
        }
    }

    fun validateLoginForm(email: String, password: String): AuthFormState {
        val emailResponse = validationUseCases.validateEmail(email = email)
        val passwordResponse = validationUseCases.validatePassword(password = password)

        val hasError = listOf(
            emailResponse,
            passwordResponse
        ).any { !it.isValid }

        return AuthFormState(
            hasNoError = !hasError,
            emailError = emailResponse.errorMessage,
            passwordError = passwordResponse.errorMessage
        )
    }

}