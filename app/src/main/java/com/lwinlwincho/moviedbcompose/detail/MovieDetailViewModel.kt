package com.lwinlwincho.moviedbcompose.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    private var _detailUiState = MutableStateFlow<MovieDetailUiState>(MovieDetailUiState.Loading)
    val detailUiState: StateFlow<MovieDetailUiState> = _detailUiState.asStateFlow()

    fun getMovieDetail(movieId: Int) {
        viewModelScope.launch {
            movieRepository.getMovieDetail(movieId)
                .catch {
                    _detailUiState.value = MovieDetailUiState.Failure(it.message.toString())
                }
                .collectLatest {
                    _detailUiState.value = MovieDetailUiState.SuccessMovieDetail(it)
                }
        }
    }
}

sealed class MovieDetailUiState {
    data class SuccessMovieDetail(val movieDetailModel: MovieDetailModel) : MovieDetailUiState()
    data object Loading : MovieDetailUiState()
    data class Failure(val message: String) : MovieDetailUiState()

}