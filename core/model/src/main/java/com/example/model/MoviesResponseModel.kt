package com.example.model

data class MoviesResponseModel<N>(
    val page: Int,
    val results: List<N>,
    val totalPages: Int,
    val totalResults: Int
)