package com.utilityhub.csapp.ui.home.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.utilityhub.csapp.core.Constants
import com.utilityhub.csapp.domain.use_case.auth.AuthUseCases
import com.utilityhub.csapp.domain.use_case.utility.UtilityUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    utilityUseCases: UtilityUseCases,
    authUseCases: AuthUseCases,
    @Named(Constants.IO_DISPATCHER)
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    val favorites =
        utilityUseCases.getFavorites().asLiveData(ioDispatcher + viewModelScope.coroutineContext)

    val isLoggedIn =
        authUseCases.isLoggedIn()

}