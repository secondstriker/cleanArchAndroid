package com.codewithmohsen.commonandroid.di

import com.codewithmohsen.domain.di.ApplicationScope
import com.codewithmohsen.domain.di.DefaultDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.*
import timber.log.Timber
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object CoroutinesScopesModule {

    /**
     * get scope from application to do something independently from our coroutine scopes.
     */
    @Singleton
    @ApplicationScope
    @Provides
    fun providesCoroutineScopeForApplication(
        @DefaultDispatcher defaultDispatcher: CoroutineDispatcher
    ): CoroutineScope = CoroutineScope(SupervisorJob() + defaultDispatcher
            + CoroutineName("ExternalCoroutineScope") +
            CoroutineExceptionHandler { _, throwable ->
            Timber.e(throwable)
    })

}