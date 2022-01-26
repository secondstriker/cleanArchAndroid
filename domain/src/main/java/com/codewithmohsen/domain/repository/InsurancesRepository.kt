package com.codewithmohsen.domain.repository

import com.codewithmohsen.common.Resource
import com.codewithmohsen.domain.entities.InsuranceResponseItem
import kotlinx.coroutines.flow.Flow


interface InsurancesRepository {

    suspend fun getAllInsurances(): Flow<Resource<List<InsuranceResponseItem>>>
}