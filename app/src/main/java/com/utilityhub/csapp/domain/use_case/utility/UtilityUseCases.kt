package com.utilityhub.csapp.domain.use_case.utility

data class UtilityUseCases(
    val getLandSpots: GetLandSpots,
    val getThrowSpots: GetThrowSpots,
    val addFavorite: AddFavorite,
    val getFavorites: GetFavorites,
    val shareTutorial: ShareTutorial
)