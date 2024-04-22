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
    private val movieAPIService: MovieAPIService,
    private val ioDispatcher: CoroutineDispatcher
) : RemoteDataSource {

    //for combine state
    /* override val nowShowingMoviesFlow: Flow<BaseResponse<MovieResponse>>
         get() = flow {
             emit(movieAPIService.getNowPlaying())
         }

     override val popularMoviesFlow: Flow<BaseResponse<MovieResponse>>
         get() = flow {
             emit(movieAPIService.getPopular())
         }*/

    //for state flow
    override suspend fun getNowPlaying(): Flow<BaseResponse<MovieResponse>> {
        return flow {
            emit(movieAPIService.getNowPlaying())
        }

        /*return flow {
            Result.runCatching {
                withContext(ioDispatcher) {
                    movieAPIService.getNowPlaying()
                }
            }
        }*/

    }

    override fun getPopular(): Flow<BaseResponse<MovieResponse>> {
        return flow {
            emit(movieAPIService.getPopular())
        }
    }

    override fun getMovieDetail(movieId: Int): Flow<MovieDetailResponse> {
        return flow {
            movieAPIService.loadMovieDetail(movieId)?.let { emit(it) }
        }
    }

    /*override fun getMovieCredits(movieId: Int): Flow<CreditResponse> {
        return flow {
            emit(movieAPIService.getCredits(movieId))
        }
    }*/
}