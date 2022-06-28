package com.utilityhub.csapp.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FieldValue
import com.utilityhub.csapp.common.Constants
import com.utilityhub.csapp.domain.model.*
import com.utilityhub.csapp.domain.repository.UtilityRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class UtilityRepositoryImpl(
    @Named(Constants.MAPS_REF) private val mapsRef: CollectionReference,
    @Named(Constants.USERS_REF) private val usersRef: CollectionReference,
    private val auth: FirebaseAuth
) : UtilityRepository {

    override fun getLandingSpots(map: String, utilityType: String) = callbackFlow {
        val landingSpotsRef = mapsRef
            .document(map)
            .collection(utilityType)
        val snapshotListener = landingSpotsRef.addSnapshotListener { snapshot, e ->
            val response = if (snapshot != null) {
                val landingSpots = snapshot.documents.mapNotNull { snapShot ->
                    snapShot.toObject(Utility::class.java)
                }.toCollection(ArrayList())
                Response.Success(landingSpots)
            } else {
                Response.Failure(e?.message ?: e.toString())
            }
            trySend(response).isSuccess
        }
        awaitClose {
            snapshotListener.remove()
        }
    }

    override fun getThrowingSpots(map: String, utilityType: String, landingSpot: String) =
        callbackFlow {
            val throwingSpotsRef = mapsRef
                .document(map)
                .collection(utilityType)
                .document(landingSpot)
                .collection(Constants.THROW_REF)
            val snapshotListener = throwingSpotsRef.addSnapshotListener { snapshot, e ->
                val response = if (snapshot != null) {
                    val throwingSpots = snapshot.documents.mapNotNull { snapShot ->
                        snapShot.toObject(UtilityThrow::class.java)
                    }.toCollection(ArrayList())
                    Response.Success(throwingSpots)
                } else {
                    Response.Failure(e?.message ?: e.toString())
                }
                trySend(response).isSuccess
            }
            awaitClose {
                snapshotListener.remove()
            }
        }

    override fun getFavorites() =
        callbackFlow {
            val userFavoritesRef = usersRef.document(auth.currentUser!!.uid)
            val snapshotListener = userFavoritesRef.addSnapshotListener { snapshot, e ->
                val response = if (snapshot != null) {
                    val favorites =
                        snapshot.toObject(UserData::class.java)!!.favorites
                    Response.Success(favorites)
                } else {
                    Response.Failure(e?.message ?: e.toString())
                }
                trySend(response).isSuccess
            }
            awaitClose {
                snapshotListener.remove()
            }
        }

    override fun getTutorial(
        map: String,
        utilityType: String,
        landingSpot: String,
        throwingSpot: String
    ) = flow {
        try {
            val tutorialRef = mapsRef
                .document(map)
                .collection(utilityType)
                .document(landingSpot)
                .collection(Constants.THROW_REF)
                .document(throwingSpot)
            tutorialRef.get().await().also {
                emit(Response.Success(it.toObject(UtilityThrow::class.java)!!))
            }
        } catch (e: Exception) {
            emit(Response.Failure(e.message ?: Constants.ERROR_MESSAGE))
        }
    }

    override fun addFavorite(
        favorite: Favorite
    ) = flow {
        try {
            val currentUserRef = usersRef.document(auth.currentUser!!.uid)
            currentUserRef.update(Constants.FAVORITES_REF, FieldValue.arrayUnion(favorite))
                .await().also {
                    emit(Response.Success(true))
                }
        } catch (e: Exception) {
            emit(Response.Failure(e.message ?: Constants.ERROR_MESSAGE))
        }
    }

    override fun deleteFavorite(
        favorite: Favorite
    ) = flow {
        try {
            val currentUserRef = usersRef.document(auth.currentUser!!.uid)
            currentUserRef.update(Constants.FAVORITES_REF, FieldValue.arrayRemove(favorite))
                .await().also {
                    emit(Response.Success(true))
                }
        } catch (e: Exception) {
            emit(Response.Failure(e.message ?: Constants.ERROR_MESSAGE))
        }
    }

}