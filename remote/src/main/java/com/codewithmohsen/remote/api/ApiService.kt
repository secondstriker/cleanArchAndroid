package com.codewithmohsen.remote.api

import com.codewithmohsen.domain.entities.domain_entities.InsuranceResponseItem
import com.codewithmohsen.domain.network.APIErrorResponse
import com.codewithmohsen.domain.network.NetworkResponse
import retrofit2.http.*


interface ApiService {

    /**
     * get insurances
     */
    @Headers(
        "deviceID: 2",
        "Content-Type: application/json",
        "Accept: application/json",
        "User-Agent: Insurance Comparator App"
    )
    //due to project requirement, we just hard code the input parameters
    @GET(Constants.TEST_URL)
    suspend fun getAllInsurances(): NetworkResponse<List<InsuranceResponseItem>, APIErrorResponse>

}