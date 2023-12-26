package com.lwinlwincho.domain.repository

import com.lwinlwincho.domain.localModel.MovieItem
import com.lwinlwincho.domain.remoteModel.CreditModel
import com.lwinlwincho.domain.remoteModel.MovieDetailModel
import com.lwinlwincho.domain.remoteModel.MovieModel
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

    fun getAllFavouriteMovies():Flow<List<MovieModel>>
/*
    fun getFavouriteById(id: Long):Flow<MovieItem>

    suspend fun insertFavouriteMovie(movie:MovieItem)

    suspend fun deleteFavouriteMovie(movie:MovieItem)*/
}