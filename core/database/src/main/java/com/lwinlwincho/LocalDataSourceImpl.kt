package com.lwinlwincho

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.lwinlwincho.data.MovieList
import com.lwinlwincho.data.datasource.LocalDataSource
import com.lwinlwincho.datastore.MovieListSerializer
import com.lwinlwincho.data.model.MovieResponse
import com.lwinlwincho.database.MovieDao
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

//val Context.movieDataStore by dataStore("movie-settings.json", MovieListSerializer)

class LocalDataSourceImpl @Inject constructor(
    private val movieDao: MovieDao,
    @ApplicationContext private val context: Context
) : LocalDataSource {

   /* private val movieDataStore: DataStore<MovieList> = context.createDataStore(
        fileName = "movie-settings.pb",
        serializer = MovieListSerializer
    )*/

    val Context.movieDataStore by dataStore("movie-settings.pb", MovieListSerializer)

    override val movieList: Flow<MovieList> get() = context.movieDataStore.data

    override suspend fun saveMovieListFromNetwork(movieModel: List<MovieResponse>) {
        context.movieDataStore.updateData {
            it.copy(
                nowShowing = movieModel,
                popular = movieModel
            )
        }
    }

    override fun getAllFavouriteMovies(): Flow<List<MovieResponse>> {
        return movieDao.getAllMovie().map {
            it.toMovieResponseList()

            /*it.map { entity ->
                MovieResponse(
                   id = entity.id,
                   posterPath = entity.posterPath,
                   title = entity.title,
                   releaseDate = entity.releaseDate,
                   voteAverage = entity.voteAverage
               )
            }*/
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