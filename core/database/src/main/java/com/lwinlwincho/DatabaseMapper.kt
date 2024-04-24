package com.lwinlwincho

import com.lwinlwincho.data.responseModel.MovieResponse
import com.lwinlwincho.roomDatabase.MovieEntity

fun MovieResponse.toMovieEntity(): MovieEntity {
    return MovieEntity(
        id = id,
        posterPath = posterPath.orEmpty(),
        title = title,
        releaseDate = releaseDate,
        voteAverage = voteAverage
    )
}

fun MovieEntity.toDataModel(): MovieResponse {
    return MovieResponse(
        id = id,
        posterPath = posterPath,
        title = title,
        releaseDate = releaseDate,
        voteAverage = voteAverage
    )
}

fun List<MovieEntity>.toDataModelList(): List<MovieResponse> {
    return this.map {
        it.toDataModel()
    }
}