package com.lwinlwincho.data.datasource

import com.lwinlwincho.data.NowShowingMovieList
import com.lwinlwincho.data.PopularMovieList
import com.lwinlwincho.data.model.MovieResponse
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {

    val nowShowingMovieList : Flow<NowShowingMovieList>
    suspend fun saveNowShowingMovieListFromNetwork(movieModel: List<MovieResponse>)

    val popularMovieList : Flow<PopularMovieList>
    suspend fun savePopularMovieListFromNetwork(movieModel: List<MovieResponse>)

    fun getAllFavouriteMovies(): Flow<List<MovieResponse>>

    fun getFavouriteById(id: Long): Flow<MovieResponse?>

    suspend fun insertFavouriteMovie(movie: MovieResponse)

    suspend fun deleteFavouriteMovie(movie: MovieResponse)
}