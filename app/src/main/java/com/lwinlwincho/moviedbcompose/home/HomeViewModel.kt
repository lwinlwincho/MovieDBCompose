package com.lwinlwincho.moviedbcompose.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lwinlwincho.domain.domainModel.MovieModel
import com.lwinlwincho.domain.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    init {
        fetchNowShowingMovie()
        fetchPopularMovie()
    }

    private var _uiState = MutableStateFlow(HomeUiState())
    val uiState = combine(
        movieRepository.popularMovies,
        movieRepository.nowShowingMovies,
        _uiState
    ) { popularMovies, nowShowingMovies, uiState ->
        uiState.copy(
            popularMovies = popularMovies.sortedByDescending { it.voteAverage },
            nowShowingMovies = nowShowingMovies.sortedByDescending { it.voteAverage }
        )
    }.stateIn(
        scope = viewModelScope,
        initialValue = HomeUiState(),
        started = SharingStarted.WhileSubscribed(5000L)
    )

    private fun fetchNowShowingMovie(){
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.fetchNowShowingMovies().onFailure {
               _uiState.update { it.copy(loading = false, errorMessage = it.errorMessage) }
            }.onSuccess {
                _uiState.update { it.copy(loading = false, errorMessage = "") }
            }
        }
    }

    private fun fetchPopularMovie(){
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.fetchPopularMovies().onFailure {
                _uiState.update { it.copy(loading = false, errorMessage = it.errorMessage) }
            }.onSuccess {
                _uiState.update { it.copy(loading = false, errorMessage = "") }
            }
        }
    }

    /* init {
         getNowShowing()
         getPopular()
     }*/

    /*private fun getNowShowing() {
        viewModelScope.launch {
            movieRepository.getNowShowingMovies()
                .catch {
                    _nowShowingUIState.value =
                        HomeUiState(error = it.message.toString(), loading = false)
                }
                .collectLatest { it ->
                    _nowShowingUIState.value =
                        HomeUiState(popularMovieList = it.sortedByDescending { it.releaseDate }, loading = false)
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
                        HomeUiState(popularMovieList = it.sortedByDescending { it.voteAverage }, loading = false)
                }
        }
    }*/
}

data class HomeUiState(
    val popularMovies: List<MovieModel> = emptyList(),
    val nowShowingMovies: List<MovieModel> = emptyList(),
    val errorMessage: String = "",
    val loading: Boolean = false
)

/*sealed class HomeUIState {

    data class SuccessNowShowing(val nowShowingMovieList: List<MovieModel>) : HomeUIState()

    data class SuccessPopular(val popularMovieList: List<MovieModel>) : HomeUIState()

    data class Failure(val message: String) : HomeUIState()

    object Loading : HomeUIState()
}*/

