package com.lwinlwincho.data.datasource

import com.lwinlwincho.data.model.MovieResponse
import com.lwinlwincho.data.model.BaseResponse
import com.lwinlwincho.data.model.CreditResponse
import com.lwinlwincho.data.model.MovieDetailResponse
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {

    //for combine state flow
    val nowShowingMoviesFlow: Flow<BaseResponse<MovieResponse>>
    val popularMoviesFlow: Flow<BaseResponse<MovieResponse>>

    // for state flow
    fun getNowPlaying(): Flow<BaseResponse<MovieResponse>>
    fun getPopular(): Flow<BaseResponse<MovieResponse>>

    fun getMovieDetail(movieId:Int): Flow<MovieDetailResponse>

    fun getMovieCredits(movieId:Int): Flow<CreditResponse>

}