package com.lwinlwincho.moviedbcompose.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lwinlwincho.domain.repository.MovieRepository
import com.lwinlwincho.domain.model.MovieModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private var _nowShowingUIState = MutableStateFlow(HomeUiState())
    val nowShowingUIState: StateFlow<HomeUiState> = _nowShowingUIState.asStateFlow()

    private var _popularUIState = MutableStateFlow(HomeUiState())
    val popularUIState: StateFlow<HomeUiState> = _popularUIState.asStateFlow()

    init {
        getNowShowing()
        getPopular()
    }

    private fun getNowShowing() {
        viewModelScope.launch {
            movieRepository.getNowShowingMovies()
                .catch {
                    _nowShowingUIState.value =
                        HomeUiState(error = it.message.toString(), loading = false)
                }
                .collectLatest { it ->
                    _nowShowingUIState.value =
                        HomeUiState(movieList = it.sortedByDescending { it.releaseDate }, loading = false)
                }
        }
    }

    private fun getPopular() {
        viewModelScope.launch {
            movieRepository.getPopularMovies()
                .catch {
                    _popularUIState.value =
                        HomeUiState(error = it.message.toString(), loading = false)
                }
                .collectLatest { it ->
                    _popularUIState.value =
                        HomeUiState(movieList = it.sortedByDescending { it.voteAverage }, loading = false)
                }
        }
    }
}

/*sealed class HomeUIState {

    data class SuccessNowShowing(val nowshowingMovieList: List<MovieModel>) : HomeUIState()

    data class SuccessPopular(val popularMovieList: List<MovieModel>) : HomeUIState()

    data class Failure(val message: String) : HomeUIState()

    object Loading : HomeUIState()
}*/

data class HomeUiState(
    val movieList: List<MovieModel> = emptyList(),
    val error: String = "",
    val loading: Boolean = true
)
