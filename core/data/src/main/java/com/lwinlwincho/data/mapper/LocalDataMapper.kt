package com.lwinlwincho.data.mapper

import com.lwinlwincho.database.MovieEntity
import com.lwinlwincho.domain.localModel.MovieItem

fun MovieEntity.toMovieItem() = MovieItem(
    id = id,
    posterPath = posterPath,
    title = title,
    releaseDate = releaseDate,
    voteAverage = voteAverage
)

fun MovieItem.toMovieEntity() = MovieEntity(
    id = id,
    posterPath = posterPath,
    title = title,
    releaseDate = releaseDate,
    voteAverage = voteAverage
)


