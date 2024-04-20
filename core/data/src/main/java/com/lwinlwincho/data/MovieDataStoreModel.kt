package com.lwinlwincho.data

import com.lwinlwincho.data.model.MovieResponse
import kotlinx.serialization.Serializable

/*@Serializable
data class MovieDataStoreModel(
    val id: Int = 0,
    val posterPath: String? = "",
    val title: String = "",
    val releaseDate: String = "",
    val vote_average: Double = 0.0
)*/

@Serializable
data class MovieList(
    val nowShowing: List<MovieResponse> = emptyList(),
    val popular: List<MovieResponse> = emptyList()
)