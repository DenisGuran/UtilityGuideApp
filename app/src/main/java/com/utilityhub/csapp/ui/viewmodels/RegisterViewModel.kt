package com.utilityhub.csapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.utilityhub.csapp.domain.model.AuthFormState
import com.utilityhub.csapp.domain.use_case.auth.AuthUseCases
import com.utilityhub.csapp.domain.use_case.auth.ValidationUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authUseCases: AuthUseCases,
    private val validationUseCases: ValidationUseCases
) : ViewModel() {

    fun createAccount(email: String, password: String, username: String) =
        liveData(Dispatchers.IO + viewModelScope.coroutineContext) {
            authUseCases.register(email, password, username).collect { response ->
                emit(response)
            }
        }

    fun addUserToFirestore() = liveData(Dispatchers.IO + viewModelScope.coroutineContext) {
        authUseCases.addFirestoreUser().collect { response ->
            emit(response)
        }
    }

    fun validateRegistrationForm(
        username: String,
        email: String,
        password: String,
        confirmedPassword: String
    ): AuthFormState {
        val usernameResponse = validationUseCases.validateUsername(username = username)
        val emailResponse = validationUseCases.validateEmail(email = email)
        val passwordResponse = validationUseCases.validatePassword(password = password)
        val confirmedPasswordResponse = validationUseCases.validateConfirmedPassword(
            password = password,
            confirmedPassword = confirmedPassword
        )
        val hasError = listOf(
            usernameResponse,
            emailResponse,
            passwordResponse,
            confirmedPasswordResponse
        ).any { !it.isValid }

        return AuthFormState(
            hasNoError = !hasError,
            usernameError = usernameResponse.errorMessage,
            emailError = emailResponse.errorMessage,
            passwordError = passwordResponse.errorMessage,
            confirmedPasswordError = confirmedPasswordResponse.errorMessage
        )
    }
}