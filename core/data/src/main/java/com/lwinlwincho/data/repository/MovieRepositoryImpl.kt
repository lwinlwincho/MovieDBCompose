package com.lwinlwincho.data.repository

import com.lwinlwincho.data.datasource.LocalDataSource
import com.lwinlwincho.domain.repository.MovieRepository
import com.lwinlwincho.data.datasource.RemoteDataSource
import com.lwinlwincho.data.mapper.toCreditModel
import com.lwinlwincho.data.mapper.toMovieDetailModel
import com.lwinlwincho.data.mapper.toMovieModelList
import com.lwinlwincho.domain.remoteModel.CreditModel
import com.lwinlwincho.domain.remoteModel.MovieDetailModel
import com.lwinlwincho.domain.remoteModel.MovieModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
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

    override fun getAllFavouriteMovies(): Flow<List<MovieModel>> {
        return localDataSource.getAllMovies().map {
            it.toMovieModelList()
        }
    }

    /*override fun getFavouriteById(id: Long): Flow<MovieModel> {
        return movieDao.getMovieById(id).map {
            it!!.toMovieItem()
        }
    }

    override suspend fun insertFavouriteMovie(movie: MovieModel) {
        movieDao.insertMovie(movie = movie.toMovieEntity())
    }

    override suspend fun deleteFavouriteMovie(movie: MovieModel) {
        movieDao.delete(movie = movie.toMovieEntity())
    }*/
}