package com.lwinlwincho.data.datasource

import com.lwinlwincho.data.model.BaseResponse
import com.lwinlwincho.data.model.MovieDetailResponse
import com.lwinlwincho.data.model.MovieResponse
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {

    suspend fun fetchNowPlaying(): Result<BaseResponse<MovieResponse>>
    suspend fun fetchPopular(): Result<BaseResponse<MovieResponse>>
    fun getMovieDetail(movieId: Int): Flow<MovieDetailResponse>

    // fun getMovieCredits(movieId:Int): Flow<CreditResponse>

}