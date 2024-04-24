package com.lwinlwincho.data.responseModel

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailResponse(

    @SerialName(value = "backdrop_path") val backdropPath: String,
    @SerialName(value = "genres") val genres: List<GenreResponse>,
    @SerialName(value = "id") val id: Long,
    @SerialName(value = "imdb_id") val imdbId: String,
    @SerialName(value = "original_language") val originalLanguage: String,
    @SerialName(value = "original_title") val originalTitle: String,
    @SerialName(value = "overview") val overview: String,
    @SerialName(value = "poster_path") val posterPath: String,
    @SerialName(value = "release_date") val releaseDate: String?,
    @SerialName(value = "runtime") val runtime: Long,
    @SerialName(value = "title") val title: String,
    @SerialName(value = "vote_average") val voteAverage: Double
    //val vote_count: Long
)
