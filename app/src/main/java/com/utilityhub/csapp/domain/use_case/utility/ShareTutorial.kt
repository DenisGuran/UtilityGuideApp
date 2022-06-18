package com.utilityhub.csapp.domain.use_case.utility

import com.utilityhub.csapp.domain.model.Tutorial
import com.utilityhub.csapp.domain.repository.UtilityRepository

class ShareTutorial(
    private val repository: UtilityRepository
) {
    operator fun invoke(tutorial: MutableList<Tutorial>) =
        repository.shareTutorial(tutorial)
}