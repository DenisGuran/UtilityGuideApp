package com.utilityhub.csapp.di

import android.app.Application
import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.utilityhub.csapp.core.Constants.MAIN_INTENT
import com.utilityhub.csapp.core.Constants.MAPS_REF
import com.utilityhub.csapp.core.Constants.USERS_REF
import com.utilityhub.csapp.data.repository.AuthRepositoryImpl
import com.utilityhub.csapp.data.repository.UtilityRepositoryImpl
import com.utilityhub.csapp.domain.repository.AuthRepository
import com.utilityhub.csapp.domain.repository.UtilityRepository
import com.utilityhub.csapp.domain.use_case.auth.*
import com.utilityhub.csapp.domain.use_case.utility.GetLandSpots
import com.utilityhub.csapp.domain.use_case.utility.GetThrowSpots
import com.utilityhub.csapp.domain.use_case.utility.UtilityUseCases
import com.utilityhub.csapp.ui.activities.MainActivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
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

    @OptIn(InternalCoroutinesApi::class)
    @Singleton
    @Provides
    @Named(MAIN_INTENT)
    fun provideMainIntent(context: Context): Intent {
        return Intent(context, MainActivity::class.java)
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
        @Named(MAPS_REF) mapsRef: CollectionReference
    ): UtilityRepository = UtilityRepositoryImpl(mapsRef)

    @Singleton
    @Provides
    fun provideAuthUseCases(repository: AuthRepository) = AuthUseCases(
        addFirestoreUser = AddFirestoreUser(repository),
        getAuthState = GetAuthState(repository),
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
        getThrowSpots = GetThrowSpots(repository)
    )
}