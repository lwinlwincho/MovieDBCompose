package com.lwinlwincho.data

import com.lwinlwincho.data.responseModel.CastResponse
import com.lwinlwincho.data.responseModel.CreditResponse
import com.lwinlwincho.data.responseModel.GenreResponse
import com.lwinlwincho.data.responseModel.MovieDetailResponse
import com.lwinlwincho.data.responseModel.MovieResponse
import com.lwinlwincho.domain.domainModel.CastModel
import com.lwinlwincho.domain.domainModel.CreditModel
import com.lwinlwincho.domain.domainModel.GenreModel
import com.lwinlwincho.domain.domainModel.MovieDetailModel
import com.lwinlwincho.domain.domainModel.MovieModel

fun MovieResponse.toDomainModel(): MovieModel {
    return MovieModel(
        id = id,
        posterPath = posterPath.orEmpty(),
        title = title,
        releaseDate = releaseDate,
        voteAverage = voteAverage
    )
}

fun List<MovieResponse>.toMovieModelList(): List<MovieModel> {
    return this.map {
        it.toDomainModel()
    }
}

fun MovieDetailResponse.toDomainModel(): MovieDetailModel {
    return MovieDetailModel(
        backdropPath = backdropPath,
        genres = genres.map {
            it.toDomainModel()
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

fun GenreResponse.toDomainModel(): GenreModel {
    return GenreModel(
        id = id,
        name = name
    )
}

fun CreditResponse.toDomainModel(): CreditModel {
    return CreditModel(
        id = id,
        cast = cast.map {
            it.toDomainModel()
        }
    )
}

fun CastResponse.toDomainModel(): CastModel {
    return CastModel(
        id = id,
        name = name,
        originalName = originalName,
        profilePath = profilePath.orEmpty(),
    )
}


fun MovieModel.toDataModel(): MovieResponse {
    return MovieResponse(
        id = id,
        posterPath = posterPath,
        title = title,
        releaseDate = releaseDate,
        voteAverage = voteAverage
    )
}

fun MovieDetailModel.toDataModel(): MovieResponse? {
    return releaseDate?.let {
        MovieResponse(
            id = id.toInt(),
            posterPath = posterPath,
            title = title,
            releaseDate = it,
            voteAverage = voteAverage
        )
    }
}

fun List<MovieModel>.toMovieResponseList(): List<MovieResponse> {
    return this.map {
        it.toDataModel()
    }
}





