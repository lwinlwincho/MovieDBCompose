package com.lwinlwincho.moviedbcompose

import com.lwinlwincho.domain.domainModel.MovieModel

object PreviewMovieData {
    val previewMovieList = (1..10).map {
        MovieModel(
            id = it,
            posterPath = "",
            title = "Movie$it",
            releaseDate = "",
            voteAverage = 0.8

        )
    }
}