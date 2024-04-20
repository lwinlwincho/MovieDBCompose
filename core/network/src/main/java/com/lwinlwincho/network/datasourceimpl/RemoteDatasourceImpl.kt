package com.lwinlwincho.network.datasourceimpl

import android.util.Log
import com.lwinlwincho.data.model.MovieResponse
import com.lwinlwincho.data.model.BaseResponse
import com.lwinlwincho.data.datasource.RemoteDataSource
import com.lwinlwincho.data.model.CreditResponse
import com.lwinlwincho.data.model.MovieDetailResponse
import com.lwinlwincho.network.MovieAPIService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoteDatasourceImpl @Inject constructor(
    private val movieAPIService: MovieAPIService
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
    override fun getNowPlaying(): Flow<BaseResponse<MovieResponse>> {
        return flow {
            emit(movieAPIService.getNowPlaying())
        }
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