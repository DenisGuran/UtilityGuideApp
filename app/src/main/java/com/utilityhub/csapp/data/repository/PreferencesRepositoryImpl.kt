package com.utilityhub.csapp.data.repository

import android.content.Context
import androidx.datastore.dataStore
import com.utilityhub.csapp.data.local.Preferences
import com.utilityhub.csapp.data.local.PreferencesSerializer
import com.utilityhub.csapp.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Singleton

val Context.dataStore by dataStore("preferences.json", PreferencesSerializer)

@Singleton
class PreferencesRepositoryImpl(private val context: Context) : PreferencesRepository {

    override suspend fun savePreferences(preferences: Preferences) {
        context.dataStore.updateData {
            it.copy(tickrate = preferences.tickrate)
        }
    }

    override suspend fun getPreferences(): Flow<Preferences> = context.dataStore.data.map {
        Preferences(
            tickrate = it.tickrate
        )
    }
}