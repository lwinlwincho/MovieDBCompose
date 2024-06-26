package com.lwinlwincho.moviedbcompose.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lwinlwincho.domain.domainModel.MovieDetailModel
import com.lwinlwincho.domain.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val movieId = savedStateHandle.get<Int>("movieId")!!

    val uiState = combine(
        movieRepository.getMovieDetail(movieId),
        movieRepository.getFavouriteById(movieId.toLong())
    ) { movieDetailModel, favouriteMovie ->
        MovieDetailUiState(
            loading = false,
            movieDetailModel = movieDetailModel,
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
    val isFavourite: Boolean = false,
    val successAdded: String = "",
    val successRemoved: String = "",
    val error: String = "",
    val loading: Boolean = true
)
