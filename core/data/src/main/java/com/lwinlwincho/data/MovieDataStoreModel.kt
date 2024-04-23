package com.lwinlwincho.data

import com.lwinlwincho.data.model.MovieResponse
import kotlinx.serialization.Serializable

@Serializable
data class NowShowingMovieList(
    val nowShowing: List<MovieResponse> = emptyList(),
  //  val popular: List<MovieResponse> = emptyList()
)


@Serializable
data class PopularMovieList(
    val popular: List<MovieResponse> = emptyList()
)