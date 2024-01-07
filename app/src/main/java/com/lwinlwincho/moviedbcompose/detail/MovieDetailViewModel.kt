package com.lwinlwincho.moviedbcompose.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lwinlwincho.domain.remoteModel.CreditModel
import com.lwinlwincho.domain.remoteModel.MovieDetailModel
import com.lwinlwincho.domain.remoteModel.MovieModel
import com.lwinlwincho.domain.repository.MovieRepository
import com.lwinlwincho.moviedbcompose.favourite.FavouriteUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val movieId = savedStateHandle.get<Int>("movieId")!!

    private var _detailUiState = MutableStateFlow(MovieDetailUiState())
    val detailUiState: StateFlow<MovieDetailUiState> = _detailUiState.asStateFlow()

    private var _castUiState = MutableStateFlow(MovieDetailUiState())
    val castUiState: StateFlow<MovieDetailUiState> = _castUiState.asStateFlow()

    private var _favouriteUiState = MutableStateFlow(MovieDetailUiState())
    val favouriteUiState: StateFlow<MovieDetailUiState> = _favouriteUiState.asStateFlow()

    var isFavourite = false

    init {
        getMovieDetail(movieId = movieId)
        getMovieCredit(movieId = movieId)
    }

    private fun getMovieDetail(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.getMovieDetail(movieId)
                .catch {
                    _detailUiState.value =
                        MovieDetailUiState(error = it.message.toString(), loading = false)
                }
                .collectLatest {
                    _detailUiState.value =
                        MovieDetailUiState(movieDetailModel = it, loading = false)
                }
        }
    }

    private fun getMovieCredit(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.getMovieCredits(movieId)
                .catch {
                    _castUiState.value =
                        MovieDetailUiState(error = it.message.toString(), loading = false)
                }
                .collectLatest {
                    _castUiState.value = MovieDetailUiState(creditModel = it, loading = false)
                }
        }
    }

    fun checkIsFavourite(movieModel: MovieDetailModel) {
        viewModelScope.launch {
            movieRepository.getFavouriteById(movieModel.id).collectLatest {
                _favouriteUiState.value =
                    MovieDetailUiState(isFavourite = it?.id == movieModel.id.toInt())
            }
        }
    }

    fun toggleFavourite(movieModel: MovieDetailModel) {
        viewModelScope.launch {
            movieRepository.getFavouriteById(movieModel.id).collectLatest {
                if (it?.id == movieModel.id.toInt()) {
                    removeFavouriteMovie(movieModel)
                } else {
                    addFavouriteMovie(movieModel)
                }
            }
        }
    }

    private fun addFavouriteMovie(movieModel: MovieDetailModel) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                movieRepository.insertFavouriteMovie(movieModel)
                _favouriteUiState.value =
                    MovieDetailUiState(successAdded = "Added Bookmark!")
            } catch (e: Exception) {
                _favouriteUiState.value =
                    MovieDetailUiState(error = e.message.toString(), loading = false)
            }
        }
    }

    private fun removeFavouriteMovie(movieModel: MovieDetailModel) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                movieRepository.deleteFavouriteMovie(movieModel)
                _favouriteUiState.value =
                    MovieDetailUiState(successRemoved = "Removed Bookmark!")
            } catch (e: Exception) {
                _favouriteUiState.value =
                    MovieDetailUiState(error = e.message.toString(), loading = false)
            }
        }
    }
}

/*sealed class MovieDetailUiState {
    data class SuccessMovieDetail(val movieDetailModel: MovieDetailModel) : MovieDetailUiState()
    data class SuccessMovieCredit(val creditModel: CreditModel) : MovieDetailUiState()
    data object Loading : MovieDetailUiState()
    data class Failure(val message: String) : MovieDetailUiState()

}*/

data class MovieDetailUiState(
    val movieDetailModel: MovieDetailModel = MovieDetailModel(
        "",
        emptyList(),
        0,
        "",
        "",
        "",
        "",
        "",
        "",
        0,
        "",
        0.0
    ),
    val creditModel: CreditModel = CreditModel(0, emptyList()),
    var isFavourite: Boolean = false,
    val successAdded: String = "",
    val successRemoved: String = "",
    val error: String = "",
    val loading: Boolean = true
)
