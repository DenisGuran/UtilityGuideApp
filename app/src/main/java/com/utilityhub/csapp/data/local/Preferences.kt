package com.utilityhub.csapp.data.local

import com.utilityhub.csapp.core.Constants
import kotlinx.serialization.Serializable

@Serializable
data class Preferences(
    val tickrate: String = Constants.TAG_128
)