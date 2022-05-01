package com.utilityhub.csapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.utilityhub.csapp.domain.use_case.auth.AuthUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val useCases: AuthUseCases
) : ViewModel() {

    val userProfile =
        useCases.getUserProfile().asLiveData(Dispatchers.IO + viewModelScope.coroutineContext)

    fun signOut() = liveData(Dispatchers.IO + viewModelScope.coroutineContext) {
        useCases.signOut().collect { response ->
            emit(response)
        }
    }

}