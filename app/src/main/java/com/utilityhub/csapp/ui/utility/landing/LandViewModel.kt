package com.utilityhub.csapp.ui.utility.landing

import androidx.lifecycle.*
import com.utilityhub.csapp.domain.use_case.utility.UtilityUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class LandViewModel @Inject constructor(
    private val utilityUseCases: UtilityUseCases
) : ViewModel() {

    private var _map = MutableLiveData("")
    val currentMap: LiveData<String> = _map

    fun setCurrentMap(currentMap: String) {
        _map.value = currentMap
    }

    fun getLandingSpots(map: String, utilityType: String) =
        utilityUseCases.getLandSpots(map, utilityType)
            .asLiveData(Dispatchers.IO + viewModelScope.coroutineContext)

}