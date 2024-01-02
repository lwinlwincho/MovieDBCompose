package com.lwinlwincho

import com.lwinlwincho.data.datasource.LocalDataSource
import com.lwinlwincho.data.mapper.toMovieModel
import com.lwinlwincho.data.model.MovieResponse
import com.lwinlwincho.database.MovieDao
import com.lwinlwincho.database.MovieEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(private val movieDao: MovieDao) : LocalDataSource {

    override fun getAllFavouriteMovies(): Flow<List<MovieResponse>> {
        return movieDao.getAllMovie().map {
            /*it.map { entity ->
                MovieResponse(
                   id = entity.id,
                   posterPath = entity.posterPath,
                   title = entity.title,
                   releaseDate = entity.releaseDate,
                   voteAverage = entity.voteAverage
               )
            }*/

            it.toMovieResponseList()
        }
    }

    override fun getFavouriteById(id: Long): Flow<MovieResponse?> {
        return movieDao.getMovieById(id).map {
            it!!.toMovieResponse()
        }
    }

    override suspend fun insertFavouriteMovie(movie: MovieResponse) {
        movieDao.insertMovie(movie.toMovieEntity())
    }

    override suspend fun deleteFavouriteMovie(movie: MovieResponse) {
        movieDao.delete(movie.toMovieEntity())
    }
}