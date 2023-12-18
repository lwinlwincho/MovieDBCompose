package com.lwinlwincho.moviedbcompose.home

interface HomeEvent {
    data class GoToDetails(val movieId: Int) : HomeEvent
}