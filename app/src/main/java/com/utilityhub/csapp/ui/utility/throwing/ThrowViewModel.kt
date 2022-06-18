package com.utilityhub.csapp.ui.utility.throwing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.utilityhub.csapp.domain.use_case.utility.UtilityUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class ThrowViewModel @Inject constructor(
    private val utilityUseCases: UtilityUseCases
) : ViewModel() {

    fun getThrowingSpots(map: String, utilityType: String, landingSpot: String) =
        utilityUseCases.getThrowSpots(map, utilityType, landingSpot)
            .asLiveData(Dispatchers.IO + viewModelScope.coroutineContext)

}