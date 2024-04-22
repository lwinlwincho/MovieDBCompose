package com.lwinlwincho.data.datasource

import com.lwinlwincho.data.model.MovieResponse
import com.lwinlwincho.data.model.BaseResponse
import com.lwinlwincho.data.model.MovieDetailResponse
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {

   // val nowShowingMoviesFlow: Flow<BaseResponse<MovieResponse>>
   // val popularMoviesFlow: Flow<BaseResponse<MovieResponse>>

    // for state flow
    suspend fun getNowPlaying(): Flow<BaseResponse<MovieResponse>>
    fun getPopular(): Flow<BaseResponse<MovieResponse>>

    fun getMovieDetail(movieId:Int): Flow<MovieDetailResponse>

   // fun getMovieCredits(movieId:Int): Flow<CreditResponse>

}