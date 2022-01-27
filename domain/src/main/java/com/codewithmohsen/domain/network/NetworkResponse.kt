package com.codewithmohsen.domain.network

import com.codewithmohsen.domain.entities.ErrorEntity

sealed class NetworkResponse<out T: Any, out E: APIErrorResponse<ErrorEntity>> {

    /**
     * Success response with body
     */
    data class Success<T: Any>(val body: T) : NetworkResponse<T, Nothing>()

    /**
     * Success response without body
     */
    data class Empty<T: Any>(val body: T?) : NetworkResponse<T, Nothing>()

    /**
     * Failure response, API error
     */
    data class APIError<E: APIErrorResponse<ErrorEntity>>(val apiErrorResponse: E) : NetworkResponse<Nothing, E>()

    /**
     * Network error
     */
    data class NetworkError(val exceptionMessage: String) : NetworkResponse<Nothing, Nothing>()

    /**
     * For example, json parsing error
     */
    data class UnknownError(val throwable: Throwable?) : NetworkResponse<Nothing, Nothing>()
}
