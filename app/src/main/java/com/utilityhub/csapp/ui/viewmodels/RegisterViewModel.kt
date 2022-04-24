package com.utilityhub.csapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.utilityhub.csapp.domain.use_case.auth.AuthUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val useCases: AuthUseCases
): ViewModel(){

    fun createAccount(email: String, password: String, username: String) = liveData(Dispatchers.IO){
        useCases.register(email, password, username).collect { response ->
            emit(response)
        }
    }

    fun addUserToFirestore() = liveData(Dispatchers.IO) {
        useCases.addFirestoreUser().collect { response ->
            emit(response)
        }
    }
}