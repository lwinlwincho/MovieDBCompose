package com.lwinlwincho.data.datasource

import com.lwinlwincho.data.NowShowingResponseList
import com.lwinlwincho.data.PopularResponseList
import com.lwinlwincho.data.model.MovieResponse
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {

    val nowShowingMovieList: Flow<NowShowingResponseList>

    val popularMovieList: Flow<PopularResponseList>

    suspend fun saveNowShowingMovieListFromNetwork(movieModel: List<MovieResponse>)

    suspend fun savePopularMovieListFromNetwork(movieModel: List<MovieResponse>)

    fun getAllFavouriteMovies(): Flow<List<MovieResponse>>

    fun getFavouriteById(id: Long): Flow<MovieResponse?>

    suspend fun insertFavouriteMovie(movie: MovieResponse)

    suspend fun deleteFavouriteMovie(movie: MovieResponse)
}