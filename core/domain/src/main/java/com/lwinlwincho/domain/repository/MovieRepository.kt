package com.lwinlwincho.domain.repository

import com.lwinlwincho.domain.model.CreditModel
import com.lwinlwincho.domain.model.MovieDetailModel
import com.lwinlwincho.domain.model.MovieModel
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    //for combine state flow
    val nowShowingMoviesFlow : Flow<List<MovieModel>>
    val popularMoviesFlow: Flow<List<MovieModel>>

    //for state flow
    fun getNowShowingMovies(): Flow<List<MovieModel>>
    fun getPopularMovies(): Flow<List<MovieModel>>

    fun getMovieDetail(moveId:Int): Flow<MovieDetailModel>

    fun getMovieCredits(moveId: Int): Flow<CreditModel>
}