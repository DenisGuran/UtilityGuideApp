package com.utilityhub.csapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.utilityhub.csapp.domain.use_case.utility.UtilityUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class LandViewModel @Inject constructor(
    private val utilityUseCases: UtilityUseCases
) : ViewModel() {

    fun getLandingSpots(map: String, utility: String) =
        utilityUseCases.getLandSpots(map, utility)
            .asLiveData(Dispatchers.IO + viewModelScope.coroutineContext)

}