package com.lwinlwincho.network.datasourceimpl

import com.lwinlwincho.data.datasource.RemoteDataSource
import com.lwinlwincho.data.model.BaseResponse
import com.lwinlwincho.data.model.MovieDetailResponse
import com.lwinlwincho.data.model.MovieResponse
import com.lwinlwincho.network.MovieAPIService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteDatasourceImpl @Inject constructor(
    private val movieAPIService: MovieAPIService
) : RemoteDataSource {

    override suspend fun fetchNowPlaying(): Result<BaseResponse<MovieResponse>> {
        return Result.runCatching {
            movieAPIService.getNowPlaying()
        }
    }

    override suspend fun fetchPopular(): Result<BaseResponse<MovieResponse>> {
        return Result.runCatching {
            movieAPIService.getPopular()
        }
    }

    override fun getMovieDetail(movieId: Int): Flow<MovieDetailResponse> {
        return flow {
            movieAPIService.loadMovieDetail(movieId)?.let { emit(it) }
        }
    }

    /*override suspend fun fetchPopular(): Flow<BaseResponse<MovieResponse>> {
        return flow {
            emit(movieAPIService.getPopular())
        }
    }*/

    /*override fun getMovieCredits(movieId: Int): Flow<CreditResponse> {
        return flow {
            emit(movieAPIService.getCredits(movieId))
        }
    }*/
}