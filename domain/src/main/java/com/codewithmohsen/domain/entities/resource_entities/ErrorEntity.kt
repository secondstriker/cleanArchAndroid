package com.codewithmohsen.domain.entities.resource_entities

sealed class ErrorEntity(open val message: String?) {

    data class ClientError(override val message: String?): ErrorEntity(message)
    data class ServerError(override val message: String?): ErrorEntity(message)
    data class NetworkError(override val message: String?): ErrorEntity(message)
    data class Unauthenticated(override val message: String?): ErrorEntity(message)
    data class UnknownError(override val message: String?): ErrorEntity(message)
}