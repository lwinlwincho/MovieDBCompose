package com.lwinlwincho.data

import com.lwinlwincho.data.model.MovieResponse
import kotlinx.serialization.Serializable

@Serializable
data class MovieList(
    val nowShowing: List<MovieResponse> = emptyList(),
    val popular: List<MovieResponse> = emptyList()
)