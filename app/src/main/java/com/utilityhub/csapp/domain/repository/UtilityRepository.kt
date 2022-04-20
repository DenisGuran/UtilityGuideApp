package com.utilityhub.csapp.domain.repository

import com.utilityhub.csapp.domain.model.Response
import com.utilityhub.csapp.domain.model.Utility
import com.utilityhub.csapp.domain.model.UtilityThrow
import kotlinx.coroutines.flow.Flow

interface UtilityRepository {

    fun getLandingSpots(map: String, utility: String): Flow<Response<ArrayList<Utility>>>

    fun getThrowingSpots(map: String, utility: String, landingSpot: String): Flow<Response<ArrayList<UtilityThrow>>>

}