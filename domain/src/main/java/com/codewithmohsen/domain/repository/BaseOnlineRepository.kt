package com.codewithmohsen.domain.repository

import com.codewithmohsen.common.Config
import com.codewithmohsen.domain.entities.resource_entities.ResourceEntity
import com.codewithmohsen.domain.di.ApplicationScope
import com.codewithmohsen.domain.di.IoDispatcher
import com.codewithmohsen.domain.entities.resource_entities.ErrorEntity
import com.codewithmohsen.common.logger.Logger
import com.codewithmohsen.domain.network.APIErrorResponse
import com.codewithmohsen.domain.network.NetworkResponse
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

abstract class BaseOnlineRepository<DomainEntity: Any, ResultEntity: Any>(
    @ApplicationScope private val externalCoroutineScope: CoroutineScope,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val logger: Logger,
    private val config: Config
) {

    private val _result = MutableStateFlow<ResourceEntity<ResultEntity>>(ResourceEntity.Loading(null))

    protected suspend fun fetch(refresh: Boolean) = withContext(ioDispatcher) {

        val apiJob = launch(ioDispatcher) { getData(refresh) }
        val longLoadingJob = launch(ioDispatcher) { longLoading() }

         apiJob.invokeOnCompletion { cause ->
             logger.d(TAG, "invokeOnCompletion")
             if (longLoadingJob.isActive) {
                 logger.d(TAG, "invokeOnCompletion long loading job is cancelled.")
                 longLoadingJob.cancel()
             }
             if(apiJob.isCancelled) {
                 logger.i(TAG, "invokeOnCompletion api job is cancelled.")
                 externalCoroutineScope.launch(ioDispatcher) {
                     setValue(ResourceEntity.Cancel(_result.value.data))
                     onGetResultCancelled()
                 }
             }
             if(!apiJob.isCancelled && cause == null) {
                 logger.i(TAG, "invokeOnCompletion succeed")
                 externalCoroutineScope.launch(ioDispatcher) {
                    onGetResultSucceed()
                }
             }
             if(!apiJob.isCancelled && cause != null) {
                 logger.d(TAG, "invokeOnCompletion failed")
                 externalCoroutineScope.launch(ioDispatcher) {
                     onGetResultFailed(cause)
                 }
             }
         }

        listOf(apiJob, longLoadingJob).joinAll()
    }

    private suspend fun getData(refresh: Boolean) {
        logger.d(TAG, "getData")
        if(refresh)
            setValue(ResourceEntity.Loading(null))
        else
            setValue(ResourceEntity.Loading(_result.value.data))

        when (val result = apiCall()) {
            is NetworkResponse.APIError -> {
                when (result.apiErrorResponse) {
                    is APIErrorResponse.ClientErrorResponse -> {
                        logger.w(TAG, "APIErrorResponse.ClientErrorResponse, " +
                                "the error is ${result.apiErrorResponse.message}")
                        setErrorValue(_result.value.data, ErrorEntity.ClientError(result.apiErrorResponse.message))
                    }
                    is APIErrorResponse.ServerErrorResponse -> {
                        logger.w(TAG, "APIErrorResponse.ServerErrorResponse, " +
                                "the error is ${result.apiErrorResponse.message}")
                        setErrorValue(_result.value.data, ErrorEntity.ServerError(result.apiErrorResponse.message))
                    }
                    is APIErrorResponse.Unauthenticated -> {
                        logger.w(TAG, "APIErrorResponse.Unauthenticated, " +
                                "the error is ${result.apiErrorResponse.message}")
                        setErrorValue(_result.value.data, ErrorEntity.Unauthenticated(result.apiErrorResponse.message))
                    }
                    is APIErrorResponse.UnexpectedErrorResponse -> {
                        logger.e(TAG, "APIErrorResponse.UnexpectedErrorResponse, " +
                                "the error is ${result.apiErrorResponse.message}")
                        setErrorValue(_result.value.data, ErrorEntity.UnknownError(result.apiErrorResponse.message))
                    }
                }

            }
            is NetworkResponse.Empty -> {
                logger.w(TAG, "NetworkResponse.Empty, the result is ${result.body}")
                setValue(ResourceEntity.Success(toResult(result.body)))
            }
            is NetworkResponse.NetworkError -> {
                logger.w(TAG, "NetworkResponse.NetworkError, the error is ${result.exceptionMessage}")
                setErrorValue(_result.value.data, ErrorEntity.NetworkError(result.exceptionMessage))
            }
            is NetworkResponse.Success -> {
                logger.i(TAG, "NetworkResponse.Success, the result is ${result.body}")
                setValue(ResourceEntity.Success(toResult(result.body)))
            }
            is NetworkResponse.UnknownError -> {
                logger.e(TAG, "NetworkResponse.UnknownError, the error is ${result.throwable?.message}")
                setErrorValue(_result.value.data, ErrorEntity.UnknownError(result.throwable?.message))
            }
        }
    }

    private suspend fun setValue(resourceEntity: ResourceEntity<ResultEntity>) {
        _result.emit(resourceEntity)
    }

    private suspend fun setErrorValue(data: ResultEntity?, errorEntity: ErrorEntity) {
        _result.emit(ResourceEntity.Error(data, errorEntity))
    }

    private suspend fun longLoading() = withContext(ioDispatcher) {
        delay(config.getLongRunningThreshold())
        if(_result.value is ResourceEntity.Loading)
            setValue(ResourceEntity.LongLoading(_result.value.data))
    }

    protected open fun getResultAsFlow() = _result.asStateFlow()
    protected abstract suspend fun apiCall(): NetworkResponse<DomainEntity,  APIErrorResponse>
    protected abstract suspend fun toResult(domainEntity: DomainEntity?): ResultEntity
    protected open suspend fun onGetResultSucceed() {}
    protected open suspend fun onGetResultFailed(cause: Throwable) {}
    protected open suspend fun onGetResultCancelled() {}

    companion object {
        private const val TAG = "BaseOnlineRepository"
    }
}