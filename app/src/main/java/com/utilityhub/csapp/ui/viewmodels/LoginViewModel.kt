package com.utilityhub.csapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.utilityhub.csapp.domain.use_case.auth.AuthUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val useCases: AuthUseCases
) : ViewModel() {

    val authState = useCases.getAuthState().asLiveData(Dispatchers.IO)

    fun firebaseSignInWithEmail(email: String, password: String) = liveData(Dispatchers.IO){
        useCases.signInWithEmail(email, password).collect { response ->
            emit(response)
        }
    }

    fun firebaseSignInWithGoogle(idToken: String) = liveData(Dispatchers.IO) {
        useCases.signInWithGoogle(idToken).collect { response ->
            emit(response)
        }
    }

    fun addUserToFirestore() = liveData(Dispatchers.IO) {
        useCases.addFirestoreUser().collect { response ->
            emit(response)
        }
    }

}