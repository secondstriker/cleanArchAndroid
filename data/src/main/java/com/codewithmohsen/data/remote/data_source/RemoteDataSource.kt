package com.codewithmohsen.data.remote.data_source

import com.codewithmohsen.domain.entities.domain_entities.InsuranceResponseItem
import com.codewithmohsen.domain.network.APIErrorResponse
import com.codewithmohsen.domain.network.NetworkResponse

interface RemoteDataSource {

    suspend fun getAllInsurances(): NetworkResponse<List<InsuranceResponseItem>, APIErrorResponse>
}