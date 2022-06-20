package com.utilityhub.csapp.ui.utility.tutorial

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.utilityhub.csapp.domain.use_case.utility.UtilityUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class TutorialViewModel @Inject constructor(
    private val utilityUseCases: UtilityUseCases
) : ViewModel() {

    fun addTutorialToFavorites(
        map: String,
        utilityType: String,
        landingSpot: String,
        throwingSpot: String
    ) = utilityUseCases.addFavorite(map, utilityType, landingSpot, throwingSpot).asLiveData(Dispatchers.IO + viewModelScope.coroutineContext)

}