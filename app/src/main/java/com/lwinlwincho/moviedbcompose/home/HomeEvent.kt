package com.lwinlwincho.moviedbcompose.home

interface HomeEvent {

    data object Back: HomeEvent
    data class GoToDetails(val movieId: Int) : HomeEvent

    data object OnFavouriteEvent : HomeEvent
}