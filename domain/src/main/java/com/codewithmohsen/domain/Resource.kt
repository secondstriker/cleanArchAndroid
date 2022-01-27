package com.codewithmohsen.domain

import com.codewithmohsen.domain.entities.ErrorEntity

sealed class Resource<out T>(open val data: T?, open val errorEntity: ErrorEntity?) {

        data class Success<T: Any>(override val data: T): Resource<T>(data, null)
        data class Error<T: Any>(override val data: T?, override val errorEntity: ErrorEntity?): Resource<T>(data, errorEntity)
        data class Loading<T: Any>(override val data: T?): Resource<T>(data, null)
        data class LongLoading<T: Any>(override val data: T?): Resource<T>(data, null)
        data class Cancel<T: Any>(override val data: T?): Resource<T>(data, null)

}