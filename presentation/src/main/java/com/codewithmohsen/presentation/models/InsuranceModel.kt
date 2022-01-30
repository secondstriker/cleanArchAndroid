package com.codewithmohsen.presentation.models

data class InsuranceModel(
    var id: Int,
    var title: String,
    var logoUrl: String,
    var price: Float,
    var discount: String,
    var discountedPrice: Float,
    var rate: Float,
    var commentsCount: Int
)