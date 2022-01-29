package com.codewithmohsen.data.di

import com.codewithmohsen.common.Config
import com.codewithmohsen.data.ConfigImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class ConfigModule {

    @Binds
    abstract fun providesConfig(configImpl: ConfigImpl): Config
}