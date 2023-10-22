package com.example.network.datasourceimpl

import com.example.model.MovieModel
import com.example.model.MoviesResponseModel
import com.example.model.datasource.RemoteDataSource
import com.example.network.MovieAPIService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoteDatasourceImpl @Inject constructor(
    private val movieAPIService: MovieAPIService
) : RemoteDataSource {

    //for combine state
    override val nowShowingMoviesFlow: Flow<MoviesResponseModel<MovieModel>>
        get() = flow {
            emit(movieAPIService.getNowPlaying())
        }

    override val popularMoviesFlow: Flow<MoviesResponseModel<MovieModel>>
        get() = flow {
            emit(movieAPIService.getPopular())
        }

    //for state flow
    override fun getNowPlaying(): Flow<MoviesResponseModel<MovieModel>> {
        return flow {
            emit(movieAPIService.getNowPlaying())
        }
    }

    override fun getPopular(): Flow<MoviesResponseModel<MovieModel>> {
        return flow {
            emit(movieAPIService.getPopular())
        }
    }
}