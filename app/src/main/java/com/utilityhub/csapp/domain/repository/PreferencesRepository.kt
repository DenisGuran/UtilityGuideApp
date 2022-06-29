package com.utilityhub.csapp.domain.repository

import com.utilityhub.csapp.data.local.Preferences
import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {

    suspend fun savePreferences(preferences: Preferences)

    suspend fun getPreferences(): Flow<Preferences>
}