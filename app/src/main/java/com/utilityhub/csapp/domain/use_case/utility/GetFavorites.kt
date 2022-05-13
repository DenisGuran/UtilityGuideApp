package com.utilityhub.csapp.domain.use_case.utility

import com.utilityhub.csapp.domain.repository.UtilityRepository

class GetFavorites(
    private val repository: UtilityRepository
) {
    operator fun invoke() = repository.getFavorites()
}