package com.lwinlwincho.data.datasource

import com.lwinlwincho.data.model.MovieResponse
import com.lwinlwincho.domain.localModel.MovieItem
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {

    fun getAllMovies(): Flow<List<MovieResponse>>
}