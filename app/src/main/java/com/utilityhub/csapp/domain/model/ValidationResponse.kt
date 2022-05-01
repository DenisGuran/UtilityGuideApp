package com.utilityhub.csapp.domain.model

data class ValidationResponse(
    val isValid : Boolean,
    val errorMessage : String? = null
)
