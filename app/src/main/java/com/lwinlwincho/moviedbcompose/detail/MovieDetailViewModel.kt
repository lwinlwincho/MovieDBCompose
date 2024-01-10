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
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val movieId = savedStateHandle.get<Int>("movieId")!!

    val uiState = combine(
        movieRepository.getMovieDetail(movieId),
        movieRepository.getMovieCredits(movieId),
        movieRepository.getFavouriteById(movieId.toLong())
    ) { movieDetailModel, creditModel, favouriteMovie ->
        MovieDetailUiState(
            loading = false,
            movieDetailModel = movieDetailModel,
            creditModel = creditModel,
            isFavourite = favouriteMovie?.id == movieId
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = MovieDetailUiState()
    )

    fun toggleFavourite() {
        viewModelScope.launch {
            if (uiState.value.isFavourite) {
                removeFavouriteMovie(uiState.value.movieDetailModel)
            } else {
                addFavouriteMovie(uiState.value.movieDetailModel)
            }
        }
    }

    private fun addFavouriteMovie(movieModel: MovieDetailModel) {
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.insertFavouriteMovie(movieModel)
        }
    }

    private fun removeFavouriteMovie(movieModel: MovieDetailModel) {
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.deleteFavouriteMovie(movieModel)
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
    val isFavourite: Boolean = false,
    val successAdded: String = "",
    val successRemoved: String = "",
    val error: String = "",
    val loading: Boolean = true
)
