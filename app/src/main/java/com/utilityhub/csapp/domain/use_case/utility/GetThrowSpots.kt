package com.utilityhub.csapp.domain.use_case.utility

import com.utilityhub.csapp.domain.repository.UtilityRepository

class GetThrowSpots(
    private val repository: UtilityRepository
) {
    operator fun invoke(map: String, utility: String, landingSpot: String) =
        repository.getThrowingSpots(map, utility, landingSpot)
}