package com.lwinlwincho.di

import com.lwinlwincho.LocalDataSourceImpl
import com.lwinlwincho.data.datasource.LocalDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalDataSourceModule {
    @Binds
    @Singleton
    abstract fun bindLocalDatasource(
        localDatasourceImpl: LocalDataSourceImpl
    ):LocalDataSource
}