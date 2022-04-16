package com.utilityhub.csapp.domain.use_case.utility

import com.utilityhub.csapp.domain.repository.UtilityRepository

class GetLandSpots(
    private val repository: UtilityRepository
) {
    operator  fun invoke(map: String, utility: String) = repository.getLandingSpots(map, utility)
}