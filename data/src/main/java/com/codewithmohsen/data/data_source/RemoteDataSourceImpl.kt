package com.codewithmohsen.data.data_source

import com.codewithmohsen.remote.api.ApiService
import com.codewithmohsen.domain.entities.domain_entities.InsuranceResponseItem
import com.codewithmohsen.domain.network.APIErrorResponse
import com.codewithmohsen.domain.network.NetworkResponse
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(private val apiService: ApiService) :
    RemoteDataSource {

    override suspend fun getAllInsurances(): NetworkResponse<List<InsuranceResponseItem>, APIErrorResponse> =
        apiService.getAllInsurances()

}