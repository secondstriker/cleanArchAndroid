package com.codewithmohsen.domain.repository

import com.codewithmohsen.domain.entities.resource_entities.ResourceEntity
import com.codewithmohsen.domain.entities.domain_entities.InsuranceResponseItem
import kotlinx.coroutines.flow.Flow


interface InsurancesRepository {

    suspend fun fetchAllInsurances()
    suspend fun getAllInsurances(): Flow<ResourceEntity<List<InsuranceResponseItem>>>
}