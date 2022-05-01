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
class ForgotPasswordViewModel @Inject constructor(
    private val useCases: AuthUseCases,
    private val validationUseCases: ValidationUseCases
) : ViewModel() {

    fun resetPassword(email: String) = liveData(Dispatchers.IO + viewModelScope.coroutineContext) {
        useCases.resetPassword(email).collect { response ->
            emit(response)
        }
    }

    fun validateEmail(email: String): AuthFormState {
        val emailResponse = validationUseCases.validateEmail(email = email)

        val hasError = !emailResponse.isValid

        return AuthFormState(
            hasNoError = !hasError,
            emailError = emailResponse.errorMessage
        )
    }

}