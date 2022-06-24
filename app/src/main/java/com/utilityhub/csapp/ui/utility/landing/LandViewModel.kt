package com.utilityhub.csapp.ui.utility.landing

import androidx.lifecycle.*
import com.utilityhub.csapp.core.Constants
import com.utilityhub.csapp.domain.use_case.utility.UtilityUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class LandViewModel @Inject constructor(
    private val utilityUseCases: UtilityUseCases,
    @Named(Constants.IO_DISPATCHER)
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private var _map = MutableLiveData("")
    val currentMap: LiveData<String> = _map

    fun setCurrentMap(currentMap: String) {
        _map.value = currentMap
    }

    fun getLandingSpots(map: String, utilityType: String) =
        utilityUseCases.getLandSpots(map, utilityType)
            .asLiveData(ioDispatcher + viewModelScope.coroutineContext)

}