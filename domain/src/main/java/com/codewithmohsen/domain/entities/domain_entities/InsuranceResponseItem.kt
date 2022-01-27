package com.codewithmohsen.domain.entities.domain_entities


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class InsuranceResponseItem(
    @Json(name = "branchNumber")
    val branchNumber: Int?,
    @Json(name = "complaintResponseTime")
    val complaintResponseTime: Double?,
    @Json(name = "description")
    val description: String?,
    @Json(name = "discountTitle")
    val discountTitle: String?,
    @Json(name = "giftTitle")
    val giftTitle: String?,
    @Json(name = "hasGift")
    val hasGift: Boolean?,
    @Json(name = "icon")
    val icon: String?,
    @Json(name = "id")
    val id: Int?,
    @Json(name = "installments")
    val installments: List<Installment>?,
    @Json(name = "prices")
    val prices: List<Price>?,
    @Json(name = "satisfaction")
    val satisfaction: Double?,
    @Json(name = "title")
    val title: String?,
    @Json(name = "wealthLevel")
    val wealthLevel: Double?
)