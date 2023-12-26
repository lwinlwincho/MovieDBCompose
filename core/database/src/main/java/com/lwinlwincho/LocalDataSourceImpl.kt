package com.lwinlwincho

import com.lwinlwincho.data.datasource.LocalDataSource
import com.lwinlwincho.data.model.MovieResponse
import com.lwinlwincho.database.MovieDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(private val movieDao: MovieDao) : LocalDataSource {

    override fun getAllMovies(): Flow<List<MovieResponse>> {
        return movieDao.getAllMovie().map {
            it.map { entity ->
                MovieResponse(
                    id = entity.id,
                    posterPath = entity.posterPath,
                    title = entity.title,
                    releaseDate = entity.releaseDate,
                    voteAverage = entity.voteAverage
                )
            }
        }
    }
}