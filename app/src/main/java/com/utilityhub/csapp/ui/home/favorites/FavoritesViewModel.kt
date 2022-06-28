package com.utilityhub.csapp.ui.home.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.utilityhub.csapp.common.Constants
import com.utilityhub.csapp.domain.use_case.auth.AuthUseCases
import com.utilityhub.csapp.domain.use_case.utility.UtilityUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val utilityUseCases: UtilityUseCases,
    authUseCases: AuthUseCases,
    @Named(Constants.IO_DISPATCHER)
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    val isLoggedIn = authUseCases.isLoggedIn()

    val favorites =
        utilityUseCases.getFavorites().asLiveData(ioDispatcher + viewModelScope.coroutineContext)

    fun getTutorial(map: String, utilityType: String, landingSpot: String, throwingSpot: String) =
        utilityUseCases.getTutorial(map, utilityType, landingSpot, throwingSpot)
            .asLiveData(ioDispatcher + viewModelScope.coroutineContext)

}