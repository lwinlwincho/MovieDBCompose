package com.lwinlwincho.moviedbcompose.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lwinlwincho.domain.model.CastModel
import com.lwinlwincho.domain.model.CreditModel
import com.lwinlwincho.domain.model.GenreModel
import com.lwinlwincho.domain.model.MovieDetailModel
import com.lwinlwincho.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private var _detailUiState = MutableStateFlow(MovieDetailUiState())
    val detailUiState: StateFlow<MovieDetailUiState> = _detailUiState.asStateFlow()

    private var _castUiState = MutableStateFlow(MovieDetailUiState())
    val castUiState: StateFlow<MovieDetailUiState> = _castUiState.asStateFlow()

    fun getMovieDetail(movieId: Int) {
        viewModelScope.launch {
            movieRepository.getMovieDetail(movieId)
                .catch {
                    _detailUiState.value =
                        MovieDetailUiState(error = it.message.toString(), loading = false)
                }
                .collectLatest {
                    _detailUiState.value = MovieDetailUiState(movieDetailModel = it, loading = false)
                }
        }
    }

    fun getMovieCredit(movieId: Int) {
        viewModelScope.launch {
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
    val error: String = "",
    val loading: Boolean = true
)
