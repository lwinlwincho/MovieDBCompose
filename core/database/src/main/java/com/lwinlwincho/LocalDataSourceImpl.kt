package com.lwinlwincho

import android.content.Context
import androidx.datastore.dataStore
import com.lwinlwincho.data.datasource.LocalDataSource
import com.lwinlwincho.datastore.MovieListSerializer
import com.lwinlwincho.data.model.MovieResponse
import com.lwinlwincho.database.MovieDao
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.dataStore by dataStore("movie-settings.json", MovieListSerializer)

class LocalDataSourceImpl @Inject constructor(
    private val movieDao: MovieDao,
    @ApplicationContext private val context: Context
) : LocalDataSource {
    override suspend fun saveMovieListFromNetwork(movieModel: List<MovieResponse>) {
        context.dataStore.updateData {
            it.copy(
                nowShowing = movieModel
            )
        }
    }

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
            it?.toMovieResponse()
        }
    }

    override suspend fun insertFavouriteMovie(movie: MovieResponse) {
        movieDao.insertMovie(movie.toMovieEntity())
    }

    override suspend fun deleteFavouriteMovie(movie: MovieResponse) {
        movieDao.delete(movie.toMovieEntity())
    }
}