package com.utilityhub.csapp.domain.repository

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

    fun getFavorites(): Flow<Response<ArrayList<*>>>

    fun addFavorite(
        map: String,
        utilityType: String,
        landingSpot: String,
        throwingSpot: String
    ): Flow<Response<Boolean>>

    fun deleteFavorite(
        map: String,
        utilityType: String,
        landingSpot: String,
        throwingSpot: String
    ): Flow<Response<Boolean>>

}