package com.codewithmohsen.commonandroid.di

import com.codewithmohsen.common.logger.Logger
import com.codewithmohsen.commonandroid.LoggerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class LoggerModule {

    @Binds
    abstract fun bindsLogger(loggerImpl: LoggerImpl): Logger
}