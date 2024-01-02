package com.lwinlwincho.data.datasource

import com.lwinlwincho.data.model.MovieResponse
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {

    fun getAllFavouriteMovies(): Flow<List<MovieResponse>>

    fun getFavouriteById(id: Long): Flow<MovieResponse?>

    suspend fun insertFavouriteMovie(movie: MovieResponse)

    suspend fun deleteFavouriteMovie(movie: MovieResponse)
}