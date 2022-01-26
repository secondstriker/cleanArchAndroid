package com.codewithmohsen.domain.entities


import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class DomainResponse : ArrayList<DomainResponseItem>()