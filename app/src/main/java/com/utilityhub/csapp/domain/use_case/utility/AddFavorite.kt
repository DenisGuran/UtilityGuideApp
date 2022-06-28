package com.utilityhub.csapp.domain.use_case.utility

import com.utilityhub.csapp.domain.model.Favorite
import com.utilityhub.csapp.domain.repository.UtilityRepository

class AddFavorite(
    private val repository: UtilityRepository
) {
    operator fun invoke(favorite: Favorite) =
        repository.addFavorite(favorite)
}