package com.codewithmohsen.domain.entities.resource_entities

sealed class ResourceEntity<out T>(open val data: T?, open val errorEntity: ErrorEntity?) {

        data class Success<T: Any>(override val data: T): ResourceEntity<T>(data, null)
        data class Error<T: Any>(override val data: T?, override val errorEntity: ErrorEntity?): ResourceEntity<T>(data, errorEntity)
        data class Loading<T: Any>(override val data: T?): ResourceEntity<T>(data, null)
        data class LongLoading<T: Any>(override val data: T?): ResourceEntity<T>(data, null)
        data class Cancel<T: Any>(override val data: T?): ResourceEntity<T>(data, null)

}