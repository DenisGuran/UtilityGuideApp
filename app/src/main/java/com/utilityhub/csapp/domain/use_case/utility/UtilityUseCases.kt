package com.utilityhub.csapp.domain.use_case.utility

data class UtilityUseCases(
    val getLandSpots: GetLandSpots,
    val getThrowSpots: GetThrowSpots,
    val addFavorite: AddFavorite,
    val deleteFavorite: DeleteFavorite,
    val getFavorites: GetFavorites,
    val getTutorial: GetTutorial
)