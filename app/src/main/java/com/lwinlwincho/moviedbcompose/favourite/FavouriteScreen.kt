package com.lwinlwincho.moviedbcompose.favourite

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.lwinlwincho.moviedbcompose.Loading
import com.lwinlwincho.moviedbcompose.PreviewData
import com.lwinlwincho.moviedbcompose.PreviewData.previewMovieList
import com.lwinlwincho.moviedbcompose.R
import com.lwinlwincho.moviedbcompose.home.HomeEvent
import com.lwinlwincho.moviedbcompose.home.MovieItem
import com.lwinlwincho.moviedbcompose.ui.theme.MovieDBComposeTheme

@Composable
fun FavouriteScreen(
    navController: NavHostController
) {

    val viewModel: FavouriteViewModel = hiltViewModel()

    val favouriteUiState by viewModel.favouriteUiState.collectAsState()

    if (favouriteUiState.loading) {
        Loading()
    }

    if (favouriteUiState.errorMessage.isNotEmpty()) {
        Toast.makeText(
            LocalContext.current, favouriteUiState.errorMessage, Toast.LENGTH_SHORT
        ).show()
    }

    if(favouriteUiState.favouriteMovies.isNotEmpty()) {
        FavouriteContent(favouriteUiState, onEvent = {
            when (it) {
                is HomeEvent.GoToDetails -> {
                    navController.navigate("detail/" + it.movieId)
                }
            }
        })
    }
}

@Composable
fun FavouriteContent(
    favouriteUiState: FavouriteUiState, onEvent: (HomeEvent) -> Unit
) {

    Row {
        Image(
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = null
        )
        Text(text = "Favourite")
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(count = 2),
        content = {
            items(
                items = favouriteUiState.favouriteMovies,
                key = { it.id },
                contentType = { "MovieItem" }
            ) { movie ->
                MovieItem(movie = movie, onEvent = onEvent)
            }
        }
    )
}

@Preview
@Composable
fun previewFavourite() {
    MovieDBComposeTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            FavouriteContent(favouriteUiState = FavouriteUiState(previewMovieList), onEvent = {})

        }
    }
}
