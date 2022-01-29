package com.codewithmohsen.domain.repository

import com.codewithmohsen.common.Config
import com.codewithmohsen.domain.Resource
import com.codewithmohsen.domain.di.ApplicationScope
import com.codewithmohsen.domain.di.IoDispatcher
import com.codewithmohsen.domain.entities.ErrorEntity
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

    private val _result = MutableStateFlow<Resource<ResultEntity>>(Resource.Loading(null))

    protected suspend fun fetch(refresh: Boolean) = withContext(ioDispatcher) {

        val apiJob = launch(ioDispatcher) { getData(refresh) }
        val longLoadingJob = launch(ioDispatcher) { longLoading() }
         apiJob.invokeOnCompletion { cause ->
             logger.d(TAG, "invokeOnCompletion")
             if (longLoadingJob.isActive) {
                 logger.d(TAG, "invokeOnCompletion long loading is cancelled.")
                 longLoadingJob.cancel()
             }
             if(apiJob.isCancelled) {
                 logger.d(TAG, "invokeOnCompletion main job is cancelled.")
                 externalCoroutineScope.launch(ioDispatcher) {
                     setValue(Resource.Cancel(_result.value.data))
                     onGetResultCancelled()
                 }
             }
             if(!apiJob.isCancelled && cause == null) {
                externalCoroutineScope.launch(ioDispatcher) {
                    onGetResultSucceed()
                }
             }
             if(!apiJob.isCancelled && cause != null) {
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
            setValue(Resource.Loading(null))
        else
            setValue(Resource.Loading(_result.value.data))

        when (val result = apiCall()) {
            is NetworkResponse.APIError -> {
                when (result.apiErrorResponse) {
                    is APIErrorResponse.ClientErrorResponse -> {
                        //
                        logger.e(TAG, "APIErrorResponse.ClientErrorResponse, " +
                                "the error is ${result.apiErrorResponse.error.message}")
                        setErrorValue(_result.value.data, result.apiErrorResponse.error)
                    }
                    is APIErrorResponse.ServerErrorResponse -> {
                        logger.w(TAG, "APIErrorResponse.ServerErrorResponse, " +
                                "the error is ${result.apiErrorResponse.error.message}")
                        setErrorValue(_result.value.data, result.apiErrorResponse.error)
                    }
                    is APIErrorResponse.Unauthenticated -> {
                        logger.w(TAG, "APIErrorResponse.Unauthenticated, " +
                                "the error is ${result.apiErrorResponse.error.message}")
                        setErrorValue(_result.value.data, result.apiErrorResponse.error)
                    }
                    is APIErrorResponse.UnexpectedErrorResponse -> {
                        logger.e(TAG, "APIErrorResponse.UnexpectedErrorResponse, " +
                                "the error is ${result.apiErrorResponse.error.message}")
                        setErrorValue(_result.value.data, result.apiErrorResponse.error)
                    }
                }

            }
            is NetworkResponse.Empty -> {
                logger.w(TAG, "NetworkResponse.Empty, the result is ${result.body}")
                setValue(Resource.Success(bodyToResult(result.body)))
            }
            is NetworkResponse.NetworkError -> {
                logger.w(TAG, "NetworkResponse.NetworkError, the error is ${result.exceptionMessage}")
                setErrorValue(_result.value.data, ErrorEntity.NetworkError(result.exceptionMessage))
            }
            is NetworkResponse.Success -> {
                logger.i(TAG, "NetworkResponse.Success, the result is ${result.body}")
                setValue(Resource.Success(bodyToResult(result.body)))
            }
            is NetworkResponse.UnknownError -> {
                logger.e(TAG, "NetworkResponse.UnknownError, the error is ${result.throwable?.message}")
                setErrorValue(_result.value.data, ErrorEntity.UnknownError(result.throwable?.message))
            }
        }
    }

    private suspend fun setValue(resource: Resource<ResultEntity>) {
        _result.emit(resource)
    }

    private suspend fun setErrorValue(data: ResultEntity?, errorEntity: ErrorEntity) {
        _result.emit(Resource.Error(data, errorEntity))
    }

    private suspend fun longLoading() = withContext(ioDispatcher) {
        delay(config.getLongRunningThreshold())
        if(_result.value is Resource.Loading)
            setValue(Resource.LongLoading(_result.value.data))
    }

    protected open fun getResultAsFlow() = _result.asStateFlow()
    protected abstract suspend fun apiCall(): NetworkResponse<DomainEntity,  APIErrorResponse<ErrorEntity>>
    protected abstract suspend fun bodyToResult(domainEntity: DomainEntity?): ResultEntity
    protected open suspend fun onGetResultSucceed() {}
    protected open suspend fun onGetResultFailed(cause: Throwable) {}
    protected open suspend fun onGetResultCancelled() {}

    companion object {
        private const val TAG = "BaseOnlineRepository"
    }
}