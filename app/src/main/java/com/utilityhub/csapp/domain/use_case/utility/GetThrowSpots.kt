package com.utilityhub.csapp.domain.use_case.utility

import com.utilityhub.csapp.domain.repository.UtilityRepository

class GetThrowSpots(
    private val repository: UtilityRepository
) {
    operator fun invoke(map: String, utilityType: String, landingSpot: String) =
        repository.getThrowingSpots(map, utilityType, landingSpot)
}