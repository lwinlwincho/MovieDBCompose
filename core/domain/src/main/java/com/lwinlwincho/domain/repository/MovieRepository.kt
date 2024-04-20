package com.lwinlwincho.domain.repository

import com.lwinlwincho.domain.remoteModel.CreditModel
import com.lwinlwincho.domain.remoteModel.MovieDetailModel
import com.lwinlwincho.domain.remoteModel.MovieModel
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    //val nowShowingMoviesFlow : Flow<List<MovieModel>>
    //val popularMoviesFlow: Flow<List<MovieModel>>

    //for state flow
    fun getNowShowingMovies(): Flow<List<MovieModel>>
    fun getPopularMovies(): Flow<List<MovieModel>>

    fun getMovieDetail(moveId:Int): Flow<MovieDetailModel>

    fun getAllFavouriteMovies():Flow<List<MovieModel>>

    fun getFavouriteById(id: Long):Flow<MovieModel?>

    suspend fun insertFavouriteMovie(movie:MovieDetailModel)

    suspend fun deleteFavouriteMovie(movie:MovieDetailModel)
}