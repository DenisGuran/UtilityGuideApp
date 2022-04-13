package com.utilityhub.csapp.domain.model

sealed class Response<out T> {

    data class Success<out T>(
        val data: T
    ) : Response<T>()

    data class Failure(
        val errorMessage: String
    ) : Response<Nothing>()
}
