package com.utilityhub.csapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
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
        utility: String,
        landingSpot: String,
        throwingSpot: String
    ) = liveData(Dispatchers.IO + viewModelScope.coroutineContext) {
        utilityUseCases.addFavorite(map, utility, landingSpot, throwingSpot).collect {
            emit(it)
        }
    }

}