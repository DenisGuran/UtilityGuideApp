package com.utilityhub.csapp.data.repository

import com.google.firebase.firestore.CollectionReference
import com.utilityhub.csapp.core.Constants
import com.utilityhub.csapp.domain.model.Response
import com.utilityhub.csapp.domain.model.Utility
import com.utilityhub.csapp.domain.model.UtilityThrow
import com.utilityhub.csapp.domain.repository.UtilityRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class UtilityRepositoryImpl(
    @Named(Constants.MAPS_REF) private val mapsRef: CollectionReference
) : UtilityRepository {

    override fun getLandingSpots(map: String, utility: String) = flow {
        try {
            val landingSpotsRef = mapsRef
                .document(map)
                .collection(utility)
            val landingSpots = landingSpotsRef.get().await().documents.mapNotNull { snapShot ->
                snapShot.toObject(Utility::class.java)
            }.toCollection(ArrayList())
            emit(Response.Success(landingSpots))
        } catch (e: Exception) {
            emit(Response.Failure(e.message ?: Constants.ERROR_MESSAGE))
        }
    }

    override fun getThrowingSpots(map: String, utility: String, landingSpot: String) = flow {
        try {
            val throwingSpotsRef = mapsRef
                .document(map)
                .collection(utility)
                .document(landingSpot)
                .collection(Constants.THROW_REF)
            val throwingSpots = throwingSpotsRef.get().await().documents.mapNotNull { snapShot ->
                snapShot.toObject(UtilityThrow::class.java)
            }.toCollection(ArrayList())
            emit(Response.Success(throwingSpots))
        } catch (e: Exception) {
            emit(Response.Failure(e.message ?: Constants.ERROR_MESSAGE))
        }
    }


}