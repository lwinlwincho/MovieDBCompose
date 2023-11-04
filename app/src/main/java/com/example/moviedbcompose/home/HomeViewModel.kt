package com.example.moviedbcompose.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.MovieModel
import com.example.domain.repository.MovieRepository
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

    private var _nowShowingUIState = MutableStateFlow<HomeUIState>(HomeUIState.Loading)
    val nowShowingUIState: StateFlow<HomeUIState> = _nowShowingUIState.asStateFlow()

    /*private var _popularUIState = MutableStateFlow<HomeUIState>(HomeUIState.Loading)
    val popularUIState: StateFlow<HomeUIState> = _popularUIState.asStateFlow()*/

    init {
        getNowShowing()
        getPopular()
    }

    private fun getNowShowing() {
        viewModelScope.launch {
            movieRepository.getNowShowingMovies()
                .catch {
                    _nowShowingUIState.value = HomeUIState.Failure(it.message.toString())
                }
                .collectLatest {
                    _nowShowingUIState.value = HomeUIState.SuccessNowShowing(it.results)
                }
        }
    }

    private fun getPopular() {
        viewModelScope.launch {
            movieRepository.getPopularMovies()
                .catch {
                    _nowShowingUIState.value = HomeUIState.Failure(it.message.toString())
                }
                .collectLatest {
                    _nowShowingUIState.value = HomeUIState.SuccessPopular(it.results)
                }
        }
    }
}

sealed class HomeUIState {

    data class SuccessNowShowing(val nowshowingMovieList: List<MovieModel>) : HomeUIState()

    data class SuccessPopular(val popularMovieList: List<MovieModel>) : HomeUIState()

    data class Failure(val message: String) : HomeUIState()

    object Loading : HomeUIState()
}
