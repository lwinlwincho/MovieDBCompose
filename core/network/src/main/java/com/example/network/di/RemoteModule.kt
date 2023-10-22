package com.example.network.di

import com.example.model.datasource.RemoteDataSource
import com.example.network.datasourceimpl.RemoteDatasourceImpl
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