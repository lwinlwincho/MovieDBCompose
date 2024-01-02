package com.lwinlwincho

import androidx.transition.Transition.MatchOrder
import com.lwinlwincho.data.model.MovieResponse
import com.lwinlwincho.database.MovieEntity

fun MovieResponse.toMovieEntity(): MovieEntity {
    return MovieEntity(
        id = id,
        posterPath = posterPath.orEmpty(),
        title = title,
        releaseDate = releaseDate,
        voteAverage = voteAverage
    )
}

fun MovieEntity.toMovieResponse(): MovieResponse {
    return MovieResponse(
        id = id,
        posterPath = posterPath,
        title = title,
        releaseDate = releaseDate,
        voteAverage = voteAverage
    )
}

fun List<MovieEntity>.toMovieResponseList(): List<MovieResponse>{
    return this.map {
        it.toMovieResponse()
    }
}