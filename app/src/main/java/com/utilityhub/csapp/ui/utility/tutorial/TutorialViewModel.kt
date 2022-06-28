package com.utilityhub.csapp.ui.utility.tutorial

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.utilityhub.csapp.core.Constants
import com.utilityhub.csapp.domain.model.Favorite
import com.utilityhub.csapp.domain.use_case.auth.AuthUseCases
import com.utilityhub.csapp.domain.use_case.utility.UtilityUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class TutorialViewModel @Inject constructor(
    private val utilityUseCases: UtilityUseCases,
    authUseCases: AuthUseCases,
    @Named(Constants.IO_DISPATCHER)
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    val isLoggedIn = authUseCases.isLoggedIn()

    val favorites =
        utilityUseCases.getFavorites().asLiveData(ioDispatcher + viewModelScope.coroutineContext)

    fun removeTutorialFromFavorites(favorite: Favorite) =
        utilityUseCases.deleteFavorite(favorite)
            .asLiveData(ioDispatcher + viewModelScope.coroutineContext)


    fun addTutorialToFavorites(
        favorite: Favorite
    ) = utilityUseCases.addFavorite(favorite)
        .asLiveData(ioDispatcher + viewModelScope.coroutineContext)

}