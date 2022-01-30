package com.codewithmohsen.data.di

import com.codewithmohsen.data.data_source.RemoteDataSource
import com.codewithmohsen.data.data_source.RemoteDataSourceImpl
import com.codewithmohsen.data.repository.InsurancesRepositoryImpl
import com.codewithmohsen.domain.repository.InsurancesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
abstract class DataModule {

    @Binds
    abstract fun bindsRemoteDataSource(remoteDataSourceImpl: RemoteDataSourceImpl): RemoteDataSource

    @Binds
    abstract fun bindsInsurancesRepository(insurancesRepositoryImpl: InsurancesRepositoryImpl):
            InsurancesRepository
}