package com.utilityhub.csapp.ui.utility.throwing

import androidx.lifecycle.*
import com.utilityhub.csapp.common.Constants
import com.utilityhub.csapp.domain.repository.PreferencesRepository
import com.utilityhub.csapp.domain.use_case.utility.UtilityUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class ThrowViewModel @Inject constructor(
    private val utilityUseCases: UtilityUseCases,
    private val preferencesRepository: PreferencesRepository,
    @Named(Constants.IO_DISPATCHER)
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private var _defaultTickrate = MutableLiveData(Constants.TAG_128)
    val defaultTickrate: LiveData<String> = _defaultTickrate

    fun setDefaultTickrate(tickrate: String) {
        _defaultTickrate.value = tickrate
    }

    fun getThrowingSpots(map: String, utilityType: String, landingSpot: String) =
        utilityUseCases.getThrowSpots(map, utilityType, landingSpot)
            .asLiveData(ioDispatcher + viewModelScope.coroutineContext)

    fun getPreferences() = liveData(ioDispatcher + viewModelScope.coroutineContext) {
        preferencesRepository.getPreferences().collect { response ->
            emit(response)
        }
    }

}