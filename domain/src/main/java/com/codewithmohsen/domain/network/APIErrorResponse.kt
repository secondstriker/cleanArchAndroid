package com.codewithmohsen.domain.network

sealed class APIErrorResponse(open val message: String) {

    /**
     * Unauthenticated User (error 401)
     */
    data class Unauthenticated(override val message: String): APIErrorResponse(message)

    /**
     * Called for [400, 500) responses, except 401.
     * In this case, we have 400 - Bad Request. The request was unacceptable,
     * often due to a missing or misconfigured parameter.
     * And 429 - Too Many Requests. You made too many requests within a window of time and have been rate limited.
     * Back off for a while.
     **/
    data class ClientErrorResponse(override val message: String) : APIErrorResponse(message)

    /**
     * Called for [500, 600)
     * In this case we have 500 - Server Error. Something went wrong on our side.
     **/
    data class ServerErrorResponse(override val message: String) : APIErrorResponse(message)

    /**
     *  Unexpected
     **/
    data class UnexpectedErrorResponse(override val message: String) : APIErrorResponse(message)
}