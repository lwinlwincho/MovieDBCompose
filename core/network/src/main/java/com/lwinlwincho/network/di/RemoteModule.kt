package com.lwinlwincho.network.di

import com.lwinlwincho.data.datasource.RemoteDataSource
import com.lwinlwincho.network.datasourceimpl.RemoteDatasourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteModule {
    @Binds
    @Singleton
    abstract fun bindRemoteDatasource(
        remoteDatasourceImpl: RemoteDatasourceImpl
    ):RemoteDataSource
}