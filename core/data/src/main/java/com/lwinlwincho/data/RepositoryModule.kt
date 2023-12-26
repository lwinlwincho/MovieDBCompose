package com.lwinlwincho.data

import com.lwinlwincho.data.repository.MovieRepositoryImpl
import com.lwinlwincho.domain.repository.MovieRepository
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
    abstract fun bindMovieRepository(
        movieRepositoryImpl: MovieRepositoryImpl
    ): MovieRepository
}
