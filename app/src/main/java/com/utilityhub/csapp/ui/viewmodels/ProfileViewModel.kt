package com.utilityhub.csapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.utilityhub.csapp.domain.use_case.auth.AuthUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val useCases: AuthUseCases
): ViewModel(){

    fun signOut() = liveData(Dispatchers.IO){
        useCases.signOut().collect { response ->
            emit(response)
        }
    }

    fun getUserProfile() = liveData(Dispatchers.IO) {
        useCases.getUserProfile().collect{ response ->
            emit(response)
        }
    }
}