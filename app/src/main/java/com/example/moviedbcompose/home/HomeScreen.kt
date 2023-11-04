package com.example.moviedbcompose.home

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.example.model.MovieModel
import com.example.moviedbcompose.LoadingScreen
import com.example.moviedbcompose.R
import com.example.network.IMAGE_URL

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel()
) {
    val nowShowingUiState by viewModel.nowShowingUIState.collectAsState()
    //val popularUiState by viewModel.popularUIState.collectAsState()

    var movieList by remember {
        mutableStateOf(emptyList<MovieModel>())
    }

    when (nowShowingUiState) {
        is HomeUIState.Loading -> {
            LoadingScreen()
        }

        is HomeUIState.SuccessNowShowing -> {
            movieList = (nowShowingUiState as HomeUIState.SuccessNowShowing).nowshowingMovieList
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(bottom = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                MovieListView(
                    "NowShowing Movie",
                    (nowShowingUiState as HomeUIState.SuccessNowShowing).nowshowingMovieList
                )
            }
        }

        is HomeUIState.SuccessPopular -> {
            movieList = (nowShowingUiState as HomeUIState.SuccessPopular).popularMovieList
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(bottom = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                MovieListView(
                    "NowShowing Movie",
                    (nowShowingUiState as HomeUIState.SuccessPopular).popularMovieList
                )
            }
        }

        is HomeUIState.Failure -> {
            Toast.makeText(
                LocalContext.current,
                (nowShowingUiState as HomeUIState.Failure).message,
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}

@Composable
fun MovieListView(title: String, movieList: List<MovieModel>) {
    Text(
        text = title,
        textAlign = TextAlign.Start,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp, start = 20.dp, top = 20.dp)
    )

    LazyRow(
        modifier = Modifier,
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(movieList) { movie ->
            MovieItemView(movie = movie)
        }
    }
}

@Composable
fun MovieItemView(movie: MovieModel) {

    var isLoading by remember { mutableStateOf(true) }
    var isError by remember { mutableStateOf(false) }
    val placeholder = painterResource(id = R.drawable.dummy)
    val isLocalInspection = LocalInspectionMode.current

    val imageLoader = rememberAsyncImagePainter(
        model = IMAGE_URL + movie.posterPath,
        onState = { state ->
            isLoading = state is AsyncImagePainter.State.Loading
            isError = state is AsyncImagePainter.State.Error
        },
    )

    ConstraintLayout(
        modifier = Modifier
            .width(130.dp)
            .wrapContentHeight()
    ) {
        val (moviePhoto, title) = createRefs()

        Card(
            modifier = Modifier
                .constrainAs(moviePhoto) {
                    top.linkTo(parent.top)
                    height = Dimension.ratio("2:3")
                }
        ) {
            Image(
                painter = if (isError.not() && !isLocalInspection) imageLoader else placeholder,
                contentDescription = movie.id.toString(),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(200.dp)
                    .height(300.dp)
            )
        }
        Text(
            text = movie.title,
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            maxLines = 2,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
                .constrainAs(title) {
                    top.linkTo(moviePhoto.bottom)
                }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun MovieItemPreview() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        // MovieItemView()
    }
}