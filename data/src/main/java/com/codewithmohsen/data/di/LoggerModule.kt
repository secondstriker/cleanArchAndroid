package com.codewithmohsen.data.di

import com.codewithmohsen.common.logger.Logger
import com.codewithmohsen.data.LoggerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class LoggerModule {

    @Binds
    abstract fun providesLogger(loggerImpl: LoggerImpl): Logger
}