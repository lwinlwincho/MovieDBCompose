package com.lwinlwincho.data.repository

import com.lwinlwincho.data.datasource.LocalDataSource
import com.lwinlwincho.data.datasource.RemoteDataSource
import com.lwinlwincho.data.mapper.toMovieDetailModel
import com.lwinlwincho.data.mapper.toMovieDetailResponse
import com.lwinlwincho.data.mapper.toMovieModel
import com.lwinlwincho.data.mapper.toMovieModelList
import com.lwinlwincho.data.model.MovieResponse
import com.lwinlwincho.domain.remoteModel.MovieDetailModel
import com.lwinlwincho.domain.remoteModel.MovieModel
import com.lwinlwincho.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : MovieRepository {

    override val popularMovies: Flow<List<MovieModel>>
        get() = localDataSource.popularMovieList.map {
            it.popular.toMovieModelList()
        }

    override val nowShowingMovies: Flow<List<MovieModel>>
        get() = localDataSource.nowShowingMovieList.map {
            it.nowShowing.toMovieModelList()
        }

    override suspend fun fetchNowShowingMovies(): Result<Unit> {
        return remoteDataSource.fetchNowPlaying().map {
            saveNowShowingMovie(it.results)
        }
    }

    override suspend fun fetchPopularMovies():  Result<Unit> {
        return remoteDataSource.fetchPopular().map {
            savePopularMovie(it.results)
        }
    }

    /*override fun getPopularMovies(): Flow<List<MovieModel>> {
        return remoteDataSource.fetchPopular().map {
            it.results.toMovieModelList()
        }
    }*/

    private suspend fun saveNowShowingMovie(movieModel: List<MovieResponse>) {
        localDataSource.saveNowShowingMovieListFromNetwork(movieModel)
    }

    private suspend fun savePopularMovie(movieModel: List<MovieResponse>) {
        localDataSource.savePopularMovieListFromNetwork(movieModel)
    }

    override fun getMovieDetail(moveId: Int): Flow<MovieDetailModel> {
        return remoteDataSource.getMovieDetail(movieId = moveId).map {
            it.toMovieDetailModel()
        }
    }

    override fun getAllFavouriteMovies(): Flow<List<MovieModel>> {
        return localDataSource.getAllFavouriteMovies().map {
            it.toMovieModelList()
        }
    }

    override fun getFavouriteById(id: Long): Flow<MovieModel?> {
        return localDataSource.getFavouriteById(id).map {
            it?.toMovieModel()
        }
    }

    override suspend fun insertFavouriteMovie(movie: MovieDetailModel) {
        movie.toMovieDetailResponse()?.let { localDataSource.insertFavouriteMovie(movie = it) }
    }

    override suspend fun deleteFavouriteMovie(movie: MovieDetailModel) {
        movie.toMovieDetailResponse()?.let { localDataSource.deleteFavouriteMovie(movie = it) }
    }
}