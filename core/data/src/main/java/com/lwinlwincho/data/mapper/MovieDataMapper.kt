package com.lwinlwincho.data.mapper

import com.lwinlwincho.data.model.CastResponse
import com.lwinlwincho.data.model.CreditResponse
import com.lwinlwincho.data.model.GenreResponse
import com.lwinlwincho.data.model.MovieDetailResponse
import com.lwinlwincho.data.model.MovieResponse
import com.lwinlwincho.domain.model.CastModel
import com.lwinlwincho.domain.model.CreditModel
import com.lwinlwincho.domain.model.GenreModel
import com.lwinlwincho.domain.model.MovieDetailModel
import com.lwinlwincho.domain.model.MovieModel

fun MovieResponse.toMovieModel(): MovieModel {
    return MovieModel(
        id = id,
        posterPath = posterPath.orEmpty(),
        title = title,
        releaseDate = releaseDate,
        voteAverage = voteAverage
    )
}

fun List<MovieResponse>.toMovieModelList():List<MovieModel>{
    return this.map {
        it.toMovieModel()
    }
}


fun CreditResponse.toCreditModel(): CreditModel {
    return CreditModel(
        id = id,
        cast = cast.map {
            it.toCastModel()
        }
    )
}

fun CastResponse.toCastModel(): CastModel {
    return CastModel(
        id = id,
        name = name,
        originalName = originalName,
        profilePath = profilePath.orEmpty(),
    )
}

fun GenreResponse.toGenreModel(): GenreModel {
    return GenreModel(
        id = id,
        name = name
    )
}

fun MovieDetailResponse.toMovieDetailModel(): MovieDetailModel {
    return MovieDetailModel(
        backdropPath = backdropPath,
        genres = genres.map {
            it.toGenreModel()
        },
        id = id,
        imdbId = imdbId,
        originalLanguage = originalLanguage,
        originalTitle = originalTitle,
        overview = overview,
        posterPath = posterPath,
        releaseDate = releaseDate.orEmpty(),
        runtime = runtime,
        title = title,
        voteAverage = voteAverage
    )
}


