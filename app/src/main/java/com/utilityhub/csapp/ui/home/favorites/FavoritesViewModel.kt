package com.utilityhub.csapp.ui.home.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.utilityhub.csapp.domain.use_case.utility.UtilityUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val utilityUseCases: UtilityUseCases
) : ViewModel() {

    val favorites = utilityUseCases.getFavorites().asLiveData(Dispatchers.IO + viewModelScope.coroutineContext)

}