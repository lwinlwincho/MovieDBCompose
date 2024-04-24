package com.lwinlwincho.data.datasource

import com.lwinlwincho.data.responseModel.BaseResponse
import com.lwinlwincho.data.responseModel.MovieDetailResponse
import com.lwinlwincho.data.responseModel.MovieResponse
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {

    suspend fun fetchNowPlaying(): Result<BaseResponse<MovieResponse>>
    suspend fun fetchPopular(): Result<BaseResponse<MovieResponse>>
    fun getMovieDetail(movieId: Int): Flow<MovieDetailResponse>

    // fun getMovieCredits(movieId:Int): Flow<CreditResponse>

}