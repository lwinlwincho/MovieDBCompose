package com.lwinlwincho.moviedbcompose

import androidx.lifecycle.ViewModel
import com.lwinlwincho.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

}