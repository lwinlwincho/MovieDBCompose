package com.example.model

import kotlinx.serialization.SerialName
import java.io.Serializable


data class MovieModel(
    @SerialName(value = "id") val id: Int,
//poster path can string or null
    @SerialName(value = "poster_path") val posterPath: String?,
    @SerialName(value = "title") val title: String,
    @SerialName(value = "release_date") val releaseDate: String,
    @SerialName(value = "vote_average") val voteAverage: Double
) : Serializable

