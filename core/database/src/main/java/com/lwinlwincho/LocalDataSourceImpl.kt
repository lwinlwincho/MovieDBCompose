package com.lwinlwincho

import android.content.Context
import androidx.datastore.dataStore
import com.lwinlwincho.data.NowShowingResponseList
import com.lwinlwincho.data.PopularResponseList
import com.lwinlwincho.data.datasource.LocalDataSource
import com.lwinlwincho.data.responseModel.MovieResponse
import com.lwinlwincho.roomDatabase.MovieDao
import com.lwinlwincho.datastore.NowShowingMovieListSerializer
import com.lwinlwincho.datastore.PopularMovieListSerializer
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.nowShowingDataStore by dataStore(
    "now_showing_movie-settings.pb",
    NowShowingMovieListSerializer
)
val Context.popularDataStore by dataStore("popular_movie-settings.pb", PopularMovieListSerializer)


class LocalDataSourceImpl @Inject constructor(
    private val movieDao: MovieDao,
    @ApplicationContext private val context: Context
) : LocalDataSource {

    override val nowShowingMovieList: Flow<NowShowingResponseList>
        get() = context.nowShowingDataStore.data

    override val popularMovieList: Flow<PopularResponseList>
        get() = context.popularDataStore.data


    override suspend fun saveNowShowingMovieListFromNetwork(movieModel: List<MovieResponse>) {
        context.nowShowingDataStore.updateData {
            it.copy(nowShowing = movieModel)
        }
    }

    override suspend fun savePopularMovieListFromNetwork(movieModel: List<MovieResponse>) {
        context.popularDataStore.updateData {
            it.copy(popular = movieModel)
        }
    }

    override fun getAllFavouriteMovies(): Flow<List<MovieResponse>> {
        return movieDao.getAllMovie().map {
            it.toDataModelList()

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
            it?.toDataModel()
        }
    }

    override suspend fun insertFavouriteMovie(movie: MovieResponse) {
        movieDao.insertMovie(movie.toMovieEntity())
    }

    override suspend fun deleteFavouriteMovie(movie: MovieResponse) {
        movieDao.delete(movie.toMovieEntity())
    }
}