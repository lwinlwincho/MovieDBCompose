package com.lwinlwincho.moviedbcompose.home

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.util.lerp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.lwinlwincho.domain.model.MovieModel
import com.lwinlwincho.moviedbcompose.Loading
import com.lwinlwincho.moviedbcompose.R
import com.lwinlwincho.network.IMAGE_URL
import kotlin.math.absoluteValue

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onEvent: (Int) -> Unit
) {
    val nowShowingUiState by viewModel.nowShowingUIState.collectAsState()
    val popularUiState by viewModel.popularUIState.collectAsState()

    HomeContent(nowShowingUiState = nowShowingUiState, popularUiState, onEvent)
}

@Composable
fun HomeContent(nowShowingUiState: HomeUiState, popularUiState: HomeUiState, onEvent: (Int) -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (nowShowingUiState.loading) {
            Loading()
        }
        if (nowShowingUiState.error.isNotEmpty()) {
            Toast.makeText(
                LocalContext.current,
                nowShowingUiState.error,
                Toast.LENGTH_SHORT
            ).show()
        }
        if (nowShowingUiState.movieList.isNotEmpty()) {

            Button(
                onClick = { onEvent }
            ) {
                Text(text = "Detial")
            }


            MovieListView(
                "NowShowing Movie",
                nowShowingUiState.movieList,
                onEvent
            )
        }
        if (popularUiState.movieList.isNotEmpty()) {
            MovieListView(
                "Popular Movie",
                popularUiState.movieList,
                onEvent
            )
        }
    }
}

@Composable
fun MovieListView(title: String, movieList: List<MovieModel>,onEvent: (Int) -> Unit) {
    Text(
        text = title,
        textAlign = TextAlign.Start,
        style = MaterialTheme.typography.headlineLarge,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp, top = 16.dp, start = 24.dp)
    )

    LazyRow(
        modifier = Modifier,
        contentPadding = PaddingValues(end = 24.dp, start = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(movieList) { movie ->
            MovieItemView(movie = movie, onEvent = onEvent)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieItemView(movie: MovieModel, onEvent: (Int) -> Unit) {

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
        val (moviePhoto, title, imgStar, starRate) = createRefs()

        Card(
            modifier = Modifier
                .constrainAs(moviePhoto) {
                    top.linkTo(parent.top)
                    height = Dimension.ratio("2:3")
                },
            onClick = { onEvent(movie.id) },
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
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Start,
            maxLines = 2,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
                .constrainAs(title) {
                    top.linkTo(moviePhoto.bottom)
                }
        )

        Image(
            painter = painterResource(id = R.drawable.ic_star_rate_24),
            contentDescription = null,
            modifier = Modifier
                .wrapContentSize()
                .padding(top = 8.dp)
                .constrainAs(imgStar) {
                    top.linkTo(title.bottom)
                    start.linkTo(title.start)
                }
        )

        Text(
            text = movie.voteAverage.toString(),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .wrapContentSize()
                .padding(top = 8.dp, start = 4.dp)
                .constrainAs(starRate) {
                    start.linkTo(imgStar.end)
                    top.linkTo(imgStar.top)
                    bottom.linkTo(imgStar.bottom)
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
        HomeContent(
            nowShowingUiState = HomeUiState(movieList = emptyList()),
            HomeUiState(),
            onEvent = {})
    }
}