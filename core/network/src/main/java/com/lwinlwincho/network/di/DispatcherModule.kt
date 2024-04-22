package com.lwinlwincho.network.di

import com.github.davidepanidev.kotlinextensions.utils.dispatchers.DefaultDispatcherProvider

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DispatcherModule {


    @Provides
    @Singleton
    fun providerDispatcherProvider(): DispatcherProvider {
        return DefaultDispatcherProvider()
    }
}