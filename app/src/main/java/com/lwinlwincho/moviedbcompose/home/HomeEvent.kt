package com.lwinlwincho.moviedbcompose.home

import com.lwinlwincho.domain.remoteModel.MovieDetailModel
import com.lwinlwincho.domain.remoteModel.MovieModel

interface HomeEvent {

    data object Back: HomeEvent
    data class GoToDetails(val movieId: Int) : HomeEvent

    data object OnFavouriteEvent : HomeEvent
}