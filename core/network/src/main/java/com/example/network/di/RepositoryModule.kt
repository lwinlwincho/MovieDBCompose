package com.example.network.di

import com.example.model.repository.MovieRepository
import com.example.model.repository.MovieRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/*@Module
@InstallIn(SingletonComponent::class)
object RepositoryModules{
    @Provides
    @Singleton
    fun providerRepository(movieAPIService: MovieAPIService):MovieRepository{
        return MovieRepositoryImpl(movieAPIService)

    }
}*/

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMovieRepositoryImpl(
        movieRepositoryImpl: MovieRepositoryImpl
    ): MovieRepository
}
