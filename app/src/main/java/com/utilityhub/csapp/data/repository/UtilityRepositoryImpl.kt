package com.utilityhub.csapp.data.repository

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FieldValue
import com.utilityhub.csapp.core.Constants
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

    override fun getFavorites() = flow {
        try {
            val userFavoritesRef = usersRef.document(auth.currentUser!!.uid)
            userFavoritesRef.get().await().also {
                val favorites = it.data!!.getValue(Constants.FAVORITES_REF) as ArrayList<*>
                emit(Response.Success(favorites))
            }
        } catch (e: Exception) {
            emit(Response.Failure(e.message ?: Constants.ERROR_MESSAGE))
        }
    }

    override fun addFavorite(
        map: String,
        utilityType: String,
        landingSpot: String,
        throwingSpot: String
    ) = flow {
        try {
            val favoriteRef = Favorite(
                map = map,
                utilityType = utilityType,
                landing = landingSpot,
                throwing = throwingSpot
            )
            val currentUserRef = usersRef.document(auth.currentUser!!.uid)
            currentUserRef.update(Constants.FAVORITES_REF, FieldValue.arrayUnion(favoriteRef))
                .await().also {
                    emit(Response.Success(true))
                }
        } catch (e: Exception) {
            emit(Response.Failure(e.message ?: Constants.ERROR_MESSAGE))
        }
    }

    override fun deleteFavorite(
        map: String,
        utilityType: String,
        landingSpot: String,
        throwingSpot: String
    ) = flow {
        try {
            val favoriteRef = Favorite(
                map = map,
                utilityType = utilityType,
                landing = landingSpot,
                throwing = throwingSpot
            )
            val currentUserRef = usersRef.document(auth.currentUser!!.uid)
            currentUserRef.update(Constants.FAVORITES_REF, FieldValue.arrayRemove(favoriteRef))
                .await().also {
                    emit(Response.Success(true))
                }
        } catch (e: Exception) {
            emit(Response.Failure(e.message ?: Constants.ERROR_MESSAGE))
        }
    }

    override fun shareTutorial(tutorial: MutableList<Tutorial>) = flow {
        try {
            val imageUris = arrayListOf<Uri>()
            val tutorialSteps = arrayListOf<String>()
            tutorial.forEach { step ->
                val imageUri = Uri.parse(step.img)
                imageUris.add(imageUri)
                tutorialSteps.add(step.details!!)
            }
            emit(Response.Success(tutorialSteps))
        } catch (e: Exception) {
            emit(Response.Failure(e.message ?: Constants.ERROR_MESSAGE))
        }
    }


}