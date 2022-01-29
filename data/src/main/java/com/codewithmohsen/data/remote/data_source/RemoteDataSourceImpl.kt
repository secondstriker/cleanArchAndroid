package com.codewithmohsen.data.remote.data_source

import com.codewithmohsen.data.remote.api.ApiService
import com.codewithmohsen.domain.entities.ErrorEntity
import com.codewithmohsen.domain.entities.domain_entities.InsuranceResponseItem
import com.codewithmohsen.domain.network.APIErrorResponse
import com.codewithmohsen.domain.network.NetworkResponse
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(private val apiService: ApiService) :
    RemoteDataSource {

    override suspend fun getAllInsurances(): NetworkResponse<List<InsuranceResponseItem>, APIErrorResponse<ErrorEntity>> =
        apiService.getAllInsurances()

}