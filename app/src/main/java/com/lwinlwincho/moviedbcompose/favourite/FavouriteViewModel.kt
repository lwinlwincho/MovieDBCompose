package com.lwinlwincho.moviedbcompose.favourite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lwinlwincho.domain.domainModel.MovieModel
import com.lwinlwincho.domain.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private var _favouriteUiState = MutableStateFlow(FavouriteUiState())
    val favouriteUiState: StateFlow<FavouriteUiState> = _favouriteUiState.asStateFlow()

    init {
        getAllFavouriteMovies()
    }

    private fun getAllFavouriteMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllFavouriteMovies()
                .catch {
                    _favouriteUiState.value =
                        FavouriteUiState(errorMessage = it.message.toString(), loading = false)
                }
                .collectLatest {
                    _favouriteUiState.value =
                        FavouriteUiState(favouriteMovies = it, loading = false)
                }
        }
    }
}

data class FavouriteUiState(
    val favouriteMovies: List<MovieModel> = emptyList(),
    val errorMessage: String = "",
    val loading: Boolean = true
)