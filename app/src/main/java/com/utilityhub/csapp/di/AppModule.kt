package com.utilityhub.csapp.di

import android.app.Application
import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.utilityhub.csapp.core.Constants.MAPS_REF
import com.utilityhub.csapp.core.Constants.USERS_REF
import com.utilityhub.csapp.data.repository.AuthRepositoryImpl
import com.utilityhub.csapp.data.repository.UtilityRepositoryImpl
import com.utilityhub.csapp.domain.repository.AuthRepository
import com.utilityhub.csapp.domain.repository.UtilityRepository
import com.utilityhub.csapp.domain.use_case.auth.*
import com.utilityhub.csapp.domain.use_case.utility.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Named
import javax.inject.Singleton

@Module
@ExperimentalCoroutinesApi
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Singleton
    @Provides
    fun provideAuthRepository(
        googleSignInClient: GoogleSignInClient,
        auth: FirebaseAuth,
        @Named(USERS_REF) usersRef: CollectionReference
    ): AuthRepository = AuthRepositoryImpl(googleSignInClient, auth, usersRef)

    @Singleton
    @Provides
    fun provideUtilityRepository(
        @Named(MAPS_REF) mapsRef: CollectionReference,
        @Named(USERS_REF) usersRef: CollectionReference,
        auth: FirebaseAuth
    ): UtilityRepository = UtilityRepositoryImpl(mapsRef, usersRef, auth)

    @Singleton
    @Provides
    fun provideAuthUseCases(repository: AuthRepository) = AuthUseCases(
        addFirestoreUser = AddFirestoreUser(repository),
        getAuthState = GetAuthState(repository),
        isLoggedIn = IsLoggedIn(repository),
        getUserProfile = GetUserProfile(repository),
        signInWithGoogle = SignInWithGoogle(repository),
        signOut = SignOut(repository),
        resetPassword = ResetPassword(repository),
        signInWithEmail = SignInWithEmail(repository),
        register = Register(repository)
    )

    @Singleton
    @Provides
    fun provideUtilityUseCases(repository: UtilityRepository) = UtilityUseCases(
        getLandSpots = GetLandSpots(repository),
        getThrowSpots = GetThrowSpots(repository),
        addFavorite = AddFavorite(repository),
        getFavorites = GetFavorites(repository)
    )

    @Singleton
    @Provides
    fun provideValidationUseCases() = ValidationUseCases(
        validateEmail = ValidateEmail(),
        validateConfirmedPassword = ValidateConfirmedPassword(),
        validatePassword = ValidatePassword(),
        validateUsername = ValidateUsername()
    )
}