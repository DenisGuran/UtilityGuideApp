package com.utilityhub.csapp.ui.utility.tutorial

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.utilityhub.csapp.core.Constants
import com.utilityhub.csapp.domain.use_case.utility.UtilityUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class TutorialViewModel @Inject constructor(
    private val utilityUseCases: UtilityUseCases,
    @Named(Constants.IO_DISPATCHER)
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    fun addTutorialToFavorites(
        map: String,
        utilityType: String,
        landingSpot: String,
        throwingSpot: String
    ) = utilityUseCases.addFavorite(map, utilityType, landingSpot, throwingSpot)
        .asLiveData(ioDispatcher + viewModelScope.coroutineContext)

}