package com.codewithmohsen.domain.entities.domain_entities


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Price(
    @Json(name = "coverAmount")
    val coverAmount: Int?,
    @Json(name = "coverID")
    val coverID: Int?,
    @Json(name = "discountedPrice")
    val discountedPrice: Int?,
    @Json(name = "durationID")
    val durationID: Int?,
    @Json(name = "durationTitle")
    val durationTitle: String?,
    @Json(name = "features")
    val features: List<Feature>?,
    @Json(name = "installmentPrice")
    val installmentPrice: Int?,
    @Json(name = "penaltyAmount")
    val penaltyAmount: Int?,
    @Json(name = "penaltyDays")
    val penaltyDays: Int?,
    @Json(name = "price")
    val price: Int?,
    @Json(name = "totalPenalty")
    val totalPenalty: Int?
)