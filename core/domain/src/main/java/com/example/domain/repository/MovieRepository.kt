package com.example.domain.repository

import com.example.model.MovieModel
import com.example.model.MoviesResponseModel
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    //for combine state flow
    val nowShowingMoviesFlow : Flow<MoviesResponseModel<MovieModel>>
    val popularMoviesFlow: Flow<MoviesResponseModel<MovieModel>>

    //for state flow
    fun getNowShowingMovies(): Flow<MoviesResponseModel<MovieModel>>
    fun getPopularMovies(): Flow<MoviesResponseModel<MovieModel>>
}