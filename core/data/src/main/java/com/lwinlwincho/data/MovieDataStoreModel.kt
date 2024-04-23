package com.lwinlwincho.data

import com.lwinlwincho.data.model.MovieResponse
import kotlinx.serialization.Serializable

@Serializable
data class NowShowingResponseList(
    val nowShowing: List<MovieResponse> = emptyList()
    //  val popular: List<MovieResponse> = emptyList()
)

@Serializable
data class PopularResponseList(
    val popular: List<MovieResponse> = emptyList()
)