package com.codewithmohsen.data.remote

import com.codewithmohsen.common.Config
import com.codewithmohsen.common.logger.Logger
import com.codewithmohsen.data.remote.data_source.RemoteDataSource
import com.codewithmohsen.domain.Resource
import com.codewithmohsen.domain.di.ApplicationScope
import com.codewithmohsen.domain.di.IoDispatcher
import com.codewithmohsen.domain.entities.ErrorEntity
import com.codewithmohsen.domain.entities.domain_entities.InsuranceResponseItem
import com.codewithmohsen.domain.network.APIErrorResponse
import com.codewithmohsen.domain.network.NetworkResponse
import com.codewithmohsen.domain.repository.BaseOnlineRepository
import com.codewithmohsen.domain.repository.InsurancesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class InsurancesRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    @ApplicationScope private val externalCoroutineScope: CoroutineScope,
    private val logger: Logger,
    private val config: Config
    ): BaseOnlineRepository<List<InsuranceResponseItem>, List<InsuranceResponseItem>>(
        externalCoroutineScope,
        ioDispatcher,
        logger,
        config),
    InsurancesRepository {

    override suspend fun fetchAllInsurances() =
        super.fetch(true)

    override suspend fun getAllInsurances(): Flow<Resource<List<InsuranceResponseItem>>> =
        super.getResultAsFlow()

    override suspend fun apiCall(): NetworkResponse<List<InsuranceResponseItem>, APIErrorResponse<ErrorEntity>> =
        remoteDataSource.getAllInsurances()

    override suspend fun bodyToResult(domainEntity: List<InsuranceResponseItem>?): List<InsuranceResponseItem> =
        domainEntity ?: mutableListOf()
}