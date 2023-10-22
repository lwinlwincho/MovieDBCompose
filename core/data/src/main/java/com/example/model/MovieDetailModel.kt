package com.example.model

data class MovieDetailModel(

    val backdropPath: String,
    val genres: List<Genre>,
    val id: Long,
    val imdbId: String,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val posterPath: String,
    val releaseDate: String?,
    val runtime: Long,
    val title: String,
    val voteAverage: Double
    //val vote_count: Long
)