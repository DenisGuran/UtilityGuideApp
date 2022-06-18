package com.utilityhub.csapp.domain.use_case.utility

import com.utilityhub.csapp.domain.repository.UtilityRepository

class GetLandSpots(
    private val repository: UtilityRepository
) {
    operator fun invoke(map: String, utilityType: String) = repository.getLandingSpots(map, utilityType)
}