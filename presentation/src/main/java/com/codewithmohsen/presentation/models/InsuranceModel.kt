package com.codewithmohsen.presentation.models

data class InsuranceModel(
    var title: String,
    var logoUrl: String,
    var price: Float,
    var discount: Float,
    var discountedPrice: Float,
    var rate: Float,
    var commentsCount: Int
)