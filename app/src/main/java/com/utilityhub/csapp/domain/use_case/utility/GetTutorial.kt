package com.utilityhub.csapp.domain.use_case.utility

import com.utilityhub.csapp.domain.repository.UtilityRepository

class GetTutorial(
    private val repository: UtilityRepository
) {
    operator fun invoke(map: String, utilityType: String, landingSpot: String, throwingSpot: String) =
        repository.getTutorial(map, utilityType, landingSpot, throwingSpot)
}