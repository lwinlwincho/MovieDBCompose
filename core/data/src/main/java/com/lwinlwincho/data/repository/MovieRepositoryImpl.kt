package com.lwinlwincho.data.repository

import com.lwinlwincho.domain.repository.MovieRepository
import com.lwinlwincho.data.datasource.RemoteDataSource
import com.lwinlwincho.data.mapper.toCreditModel
import com.lwinlwincho.data.mapper.toMovieDetailModel
import com.lwinlwincho.data.mapper.toMovieEntity
import com.lwinlwincho.data.mapper.toMovieItem
import com.lwinlwincho.data.mapper.toMovieModelList
import com.lwinlwincho.database.MovieDao
import com.lwinlwincho.database.MovieEntity
import com.lwinlwincho.domain.localModel.MovieItem
import com.lwinlwincho.domain.remoteModel.CreditModel
import com.lwinlwincho.domain.remoteModel.MovieDetailModel
import com.lwinlwincho.domain.remoteModel.MovieModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val movieDao: MovieDao
) : MovieRepository {

    override val nowShowingMoviesFlow: Flow<List<MovieModel>>
        get() = remoteDataSource.nowShowingMoviesFlow.map {
            it.results.toMovieModelList()
        }

    override val popularMoviesFlow: Flow<List<MovieModel>>
        get() = remoteDataSource.popularMoviesFlow.map {
            it.results.toMovieModelList()
        }

    override fun getNowShowingMovies(): Flow<List<MovieModel>> {
        return remoteDataSource.getNowPlaying().map {
            it.results.toMovieModelList()
        }
    }

    override fun getPopularMovies(): Flow<List<MovieModel>> {
        return remoteDataSource.getPopular().map {
            it.results.toMovieModelList()
        }
    }

    override fun getMovieDetail(moveId: Int): Flow<MovieDetailModel> {
        return remoteDataSource.getMovieDetail(movieId = moveId).map {
            it.toMovieDetailModel()
        }
    }

    override fun getMovieCredits(moveId: Int): Flow<CreditModel> {
        return remoteDataSource.getMovieCredits(movieId = moveId).map {
            it.toCreditModel()
        }
    }

    override fun getAllFavouriteMovies(): Flow<List<MovieItem>> {
        return movieDao.getAllMovie().map {
            it.map(MovieEntity::toMovieItem)
        }
    }

    override fun getFavouriteById(id: Long): Flow<MovieItem> {
        return movieDao.getMovieById(id).map {
            it!!.toMovieItem()
        }
    }

    override suspend fun insertFavouriteMovie(movie: MovieItem) {
        movieDao.insertMovie(movie = movie.toMovieEntity())
    }

    override suspend fun deleteFavouriteMovie(movie: MovieItem) {
        movieDao.delete(movie = movie.toMovieEntity())
    }
}