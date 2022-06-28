package com.utilityhub.csapp.domain.repository

import com.utilityhub.csapp.domain.model.Favorite
import com.utilityhub.csapp.domain.model.Response
import com.utilityhub.csapp.domain.model.Utility
import com.utilityhub.csapp.domain.model.UtilityThrow
import kotlinx.coroutines.flow.Flow

interface UtilityRepository {

    fun getLandingSpots(map: String, utilityType: String): Flow<Response<ArrayList<Utility>>>

    fun getThrowingSpots(
        map: String,
        utilityType: String,
        landingSpot: String
    ): Flow<Response<ArrayList<UtilityThrow>>>

    fun getFavorites(): Flow<Response<MutableList<Favorite>?>>

    fun getTutorial(
        map: String,
        utilityType: String,
        landingSpot: String,
        throwingSpot: String
    ): Flow<Response<UtilityThrow>>

    fun addFavorite(
        favorite: Favorite
    ): Flow<Response<Boolean>>

    fun deleteFavorite(
        favorite: Favorite
    ): Flow<Response<Boolean>>

}