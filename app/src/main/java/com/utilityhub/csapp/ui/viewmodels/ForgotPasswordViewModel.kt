package com.utilityhub.csapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.utilityhub.csapp.domain.use_case.auth.AuthUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val useCases: AuthUseCases
): ViewModel(){
    fun resetPassword(email: String) = liveData(Dispatchers.IO){
        useCases.resetPassword(email).collect{ response ->
            emit(response)
        }
    }
}