package com.lwinlwincho.domain.remoteModel


data class MovieModel(
    val id: Int,
    val posterPath: String,
    val title: String,
    val releaseDate: String,
    val voteAverage: Double
)

