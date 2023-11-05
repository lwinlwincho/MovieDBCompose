package com.lwinlwincho.network

import com.lwinlwincho.data.model.CreditResponse
import com.lwinlwincho.data.model.MovieDetailResponse
import com.lwinlwincho.data.model.MovieResponse
import com.lwinlwincho.data.model.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val API_KEY = "9c9e4b9082cd70edd1ed7afab4f198b6"

const val MOVIE_BASE_URL = "https://api.themoviedb.org/3/movie/"

const val IMAGE_URL = "https://image.tmdb.org/t/p/w500/"

interface MovieAPIService {

    @GET("now_playing")
    suspend fun getNowPlaying(@Query("api_key") apiKey: String = API_KEY): BaseResponse<MovieResponse>

    @GET("popular")
    suspend fun getPopular(@Query("api_key") apiKey: String = API_KEY): BaseResponse<MovieResponse>

    @GET("{movie_id}")
    suspend fun loadMovieDetail(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): MovieDetailResponse?

    @GET("{movie_id}/credits")
    suspend fun getCredits(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): CreditResponse

}