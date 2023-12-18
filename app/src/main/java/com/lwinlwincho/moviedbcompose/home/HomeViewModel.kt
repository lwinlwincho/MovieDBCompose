package com.lwinlwincho.moviedbcompose.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lwinlwincho.domain.repository.MovieRepository
import com.lwinlwincho.domain.model.MovieModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

@HiltViewModel
class HomeViewModel @Inject constructor(
    movieRepository: MovieRepository
) : ViewModel() {
    /*
        private var _nowShowingUIState = MutableStateFlow(HomeUiState())
        val nowShowingUIState: StateFlow<HomeUiState> = _nowShowingUIState.asStateFlow()

        private var _popularUIState = MutableStateFlow(HomeUiState())
        val popularUIState: StateFlow<HomeUiState> = _popularUIState.asStateFlow()*/

    private val _errorMessage = MutableStateFlow("")
    val uiState = combine(
        _errorMessage,
        movieRepository.getPopularMovies(),
        movieRepository.getNowShowingMovies()
    ) { errorMessage, popularMovies, nowShowingMovies ->
        HomeUiState(
            errorMessage = errorMessage,
            loading = false,
            popularMovies = popularMovies,
            nowShowingMovies = nowShowingMovies
        )
    }.catch {error->
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

/*sealed class HomeUIState {

    data class SuccessNowShowing(val nowshowingMovieList: List<MovieModel>) : HomeUIState()

    data class SuccessPopular(val popularMovieList: List<MovieModel>) : HomeUIState()

    data class Failure(val message: String) : HomeUIState()

    object Loading : HomeUIState()
}*/

data class HomeUiState(
    val popularMovies: List<MovieModel> = emptyList(),
    val nowShowingMovies: List<MovieModel> = emptyList(),
    val errorMessage: String = "Error Message",
    val loading: Boolean = true
)
