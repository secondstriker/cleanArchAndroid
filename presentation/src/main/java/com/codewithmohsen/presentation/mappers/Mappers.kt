package com.codewithmohsen.presentation.mappers

import com.codewithmohsen.domain.entities.domain_entities.InsuranceResponseItem
import com.codewithmohsen.domain.entities.resource_entities.ResourceEntity
import com.codewithmohsen.presentation.models.InsuranceModel

fun InsuranceResponseItem.map(): InsuranceModel =
    InsuranceModel(
        id = this.id ?: 0,
        title = this.title ?: "",
        logoUrl = this.icon ?: "",
        //because we don't need to cover filters, we just get the first item in the prices list.
        price = (this.prices?.get(0)?.price ?: 0).toFloat(),
        //we hardcode discount due to project requirement
        discount = "10%",
        discountedPrice = (this.prices?.get(0)?.price ?: 0) * 0.9F,
        rate = this.wealthLevel?.toFloat() ?: 0F,
        //we hardcode commentsCount because there is no comments count in API
        commentsCount = 0
    )

fun ResourceEntity<List<InsuranceResponseItem>>.transform(): ResourceEntity<List<InsuranceModel>> {
    val transformedData = this.data?.map { it.map() }

    return when(this) {
        is ResourceEntity.Cancel -> ResourceEntity.Cancel(transformedData)
        is ResourceEntity.Error -> ResourceEntity.Error(transformedData, this.errorEntity)
        is ResourceEntity.Loading -> ResourceEntity.Loading(transformedData)
        is ResourceEntity.LongLoading -> ResourceEntity.LongLoading(transformedData)
        is ResourceEntity.Success -> ResourceEntity.Success(transformedData ?: mutableListOf())
    }

}