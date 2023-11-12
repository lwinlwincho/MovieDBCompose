package com.lwinlwincho.moviedbcompose.home

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
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
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.lwinlwincho.domain.model.MovieModel
import com.lwinlwincho.moviedbcompose.Loading
import com.lwinlwincho.moviedbcompose.MovieScreenRoute
import com.lwinlwincho.moviedbcompose.R
import com.lwinlwincho.network.IMAGE_URL
import kotlin.math.absoluteValue

@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val nowShowingUiState by viewModel.nowShowingUIState.collectAsState()
    val popularUiState by viewModel.popularUIState.collectAsState()

    HomeContent(nowShowingUiState = nowShowingUiState, popularUiState, navController)
}

@Composable
fun HomeContent(
    nowShowingUiState: HomeUiState,
    popularUiState: HomeUiState,
    navController: NavHostController
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 20.dp, top = 20.dp),
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
            PagerScreen(movieList = nowShowingUiState.movieList)
            MovieListView(
                "NowShowing Movie",
                nowShowingUiState.movieList,
                navController
            )
        }
        if (popularUiState.movieList.isNotEmpty()) {
            MovieListView(
                "Popular Movie",
                popularUiState.movieList,
                navController
            )
        }
    }
}

@Composable
fun MovieListView(title: String, movieList: List<MovieModel>, navController: NavHostController) {
    Text(
        text = title,
        textAlign = TextAlign.Start,
        style = MaterialTheme.typography.headlineLarge,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp, start = 24.dp)
    )

    LazyRow(
        modifier = Modifier,
        contentPadding = PaddingValues(end = 24.dp, start = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(movieList) { movie ->
            MovieItemView(movie = movie, navController)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieItemView(movie: MovieModel, navController: NavHostController) {

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
                }
                .clickable { navController.navigate("detail/" + movie.id) },
            // onClick = { onEvent(movie.id) },

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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagerScreen(movieList: List<MovieModel>) {

    val pagerState = rememberPagerState(
        pageCount = { movieList.size },
        initialPage = movieList.size / 2
    )

    var isLoading by remember { mutableStateOf(true) }
    var isError by remember { mutableStateOf(false) }
    val placeholder = painterResource(id = R.drawable.dummy)
    val isLocalInspection = LocalInspectionMode.current

    HorizontalPager(
        state = pagerState,
        contentPadding = PaddingValues(start = 36.dp, end = 36.dp),
        modifier = Modifier.fillMaxSize()
    ) { page ->
        Card(
            modifier = Modifier
                .graphicsLayer {
                    // Calculate the absolute offset for the current page from the
                    // scroll position. We use the absolute value which allows us to mirror
                    // any effects for both directions
                    val pageOffset =
                        ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue

                    // We animate the scaleX + scaleY, between 85% and 100%
                    lerp(
                        start = 0.85f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    ).also { scale ->
                        scaleX = scale
                        scaleY = scale
                    }

                    // We animate the alpha, between 50% and 100%
                    alpha = lerp(
                        start = 0.5f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    )
                }
        ) {

            val imageLoader = rememberAsyncImagePainter(
                model = IMAGE_URL + movieList[page].posterPath,
                onState = { state ->
                    isLoading = state is AsyncImagePainter.State.Loading
                    isError = state is AsyncImagePainter.State.Error
                }
            )

            Box {
                Image(
                    painter = if (isError.not() && !isLocalInspection) imageLoader else placeholder,
                    contentDescription = null,
                    modifier = Modifier
                        .width(300.dp)
                        .height(200.dp),
                    contentScale = ContentScale.Crop,
                )
            }
        }
    }

    Row(
        Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(bottom = 8.dp, top = 8.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(pagerState.pageCount) { iteration ->
            val color =
                if (pagerState.currentPage == iteration) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.primary
            Box(
                modifier = Modifier
                    .padding(2.dp)
                    .clip(CircleShape)
                    .background(color)
                    .size(4.dp)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MovieItemPreview() {
    /* Surface(
         modifier = Modifier.fillMaxSize(),
         color = MaterialTheme.colorScheme.background
     ) {
         HomeContent(
             nowShowingUiState = HomeUiState(movieList = emptyList()),
             HomeUiState(),
             onEvent = {},
             NavHostController())
     }*/
}