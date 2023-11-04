package com.example.model.repository

import com.example.domain.repository.MovieRepository
import com.example.model.MovieModel
import com.example.model.MoviesResponseModel
import com.example.model.datasource.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
) : MovieRepository {

    override val nowShowingMoviesFlow: Flow<MoviesResponseModel<MovieModel>>
        get() = remoteDataSource.nowShowingMoviesFlow

    override val popularMoviesFlow: Flow<MoviesResponseModel<MovieModel>>
        get() = remoteDataSource.popularMoviesFlow

    override fun getNowShowingMovies(): Flow<MoviesResponseModel<MovieModel>> {
        return remoteDataSource.getNowPlaying()
    }

    override fun getPopularMovies(): Flow<MoviesResponseModel<MovieModel>> {
        return remoteDataSource.getPopular()
    }
}