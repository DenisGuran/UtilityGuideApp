package com.utilityhub.csapp.domain.use_case.utility

import com.utilityhub.csapp.domain.repository.UtilityRepository

class AddFavorite(
    private val repository: UtilityRepository
) {
    operator fun invoke(map: String, utility: String, landingSpot: String, throwingSpot: String) =
        repository.addToFavorites(map, utility, landingSpot, throwingSpot)
}