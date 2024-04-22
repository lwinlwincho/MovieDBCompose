package com.lwinlwincho.moviedbcompose.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lwinlwincho.domain.remoteModel.MovieModel
import com.lwinlwincho.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    movieRepository: MovieRepository
) : ViewModel() {

    private val _errorMessage = MutableStateFlow("")
    private val _loading = MutableStateFlow(true)

    init {
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.fetchNowShowingMovies().onFailure {
                _errorMessage.value = it.message.toString()
            }.onSuccess {
                _loading.value = false
            }
        }
    }

    val uiState = combine(
        _errorMessage,
        movieRepository.getPopularMovies(),
        movieRepository.nowShowingMovies
    ) { errorMessage, popularMovies, nowShowingMovies ->
        HomeUiState(
            errorMessage = errorMessage,
            loading = false,
            popularMovies = popularMovies.sortedByDescending { it.voteAverage },
            nowShowingMovies = nowShowingMovies.sortedByDescending { it.voteAverage }
        )
    }.catch { error ->
        //if it is obj,set data with ".update{}" else ".value"
        //_errorMessage.update { error.message.toString() }
        _errorMessage.value = error.message.toString()
    }.stateIn(
        scope = viewModelScope,
        initialValue = HomeUiState(),
        started = SharingStarted.WhileSubscribed(5000L)
    )

    fun clearErrorMessage() {
        //_errorMessage.update { "" }
        _errorMessage.value = ""
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
    val loading: Boolean = true
)

/*sealed class HomeUIState {

    data class SuccessNowShowing(val nowshowingMovieList: List<MovieModel>) : HomeUIState()

    data class SuccessPopular(val popularMovieList: List<MovieModel>) : HomeUIState()

    data class Failure(val message: String) : HomeUIState()

    object Loading : HomeUIState()
}*/

