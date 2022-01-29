package com.codewithmohsen.domain.repository

import com.codewithmohsen.domain.Resource
import com.codewithmohsen.domain.entities.domain_entities.InsuranceResponseItem
import kotlinx.coroutines.flow.Flow


interface InsurancesRepository {

    suspend fun fetchAllInsurances()
    suspend fun getAllInsurances(): Flow<Resource<List<InsuranceResponseItem>>>
}