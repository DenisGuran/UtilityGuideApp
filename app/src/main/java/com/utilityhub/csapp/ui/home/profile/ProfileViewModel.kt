package com.utilityhub.csapp.ui.home.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.utilityhub.csapp.data.local.Preferences
import com.utilityhub.csapp.common.Constants
import com.utilityhub.csapp.domain.repository.PreferencesRepository
import com.utilityhub.csapp.domain.use_case.auth.AuthUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authUseCases: AuthUseCases,
    private val preferencesRepository: PreferencesRepository,
    @Named(Constants.IO_DISPATCHER)
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    val isLoggedIn =
        authUseCases.isLoggedIn()

    val userProfile =
        authUseCases.getUserProfile().asLiveData(ioDispatcher + viewModelScope.coroutineContext)

    fun getPreferences() = liveData(ioDispatcher + viewModelScope.coroutineContext) {
        preferencesRepository.getPreferences().collect { response ->
            emit(response)
        }
    }

    fun savePreferences(preferences: Preferences) {
        viewModelScope.launch(ioDispatcher) {
            preferencesRepository.savePreferences(preferences = preferences)
        }
    }

    fun signOut() = liveData(ioDispatcher + viewModelScope.coroutineContext) {
        authUseCases.signOut().collect { response ->
            emit(response)
        }
    }

}