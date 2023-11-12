package com.lwinlwincho.data.repository

import com.lwinlwincho.domain.repository.MovieRepository
import com.lwinlwincho.data.datasource.RemoteDataSource
import com.lwinlwincho.data.mapper.toMovieDetailModel
import com.lwinlwincho.data.mapper.toMovieModelList
import com.lwinlwincho.domain.model.MovieDetailModel
import com.lwinlwincho.domain.model.MovieModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
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

    override fun getMovieDetail(moveId:Int): Flow<MovieDetailModel> {
        return remoteDataSource.getMovieDetail(movieId = moveId).map {
            it.toMovieDetailModel()
        }
    }
}