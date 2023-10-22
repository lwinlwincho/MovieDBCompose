package com.example.model.datasource

import com.example.model.MovieModel
import com.example.model.MoviesResponseModel
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {

    //for combine state flow
    val nowShowingMoviesFlow: Flow<MoviesResponseModel<MovieModel>>
    val popularMoviesFlow: Flow<MoviesResponseModel<MovieModel>>

    // for state flow
    fun getNowPlaying(): Flow<MoviesResponseModel<MovieModel>>
    fun getPopular(): Flow<MoviesResponseModel<MovieModel>>

}