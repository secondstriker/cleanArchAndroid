package com.codewithmohsen.domain.entities


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Installment(
    @Json(name = "month")
    val month: Int?,
    @Json(name = "percent")
    val percent: Double?
)