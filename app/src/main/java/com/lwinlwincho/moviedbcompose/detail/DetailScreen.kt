package com.lwinlwincho.moviedbcompose.detail

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.layoutId
import androidx.hilt.navigation.compose.hiltViewModel
import com.lwinlwincho.domain.remoteModel.CastModel
import com.lwinlwincho.domain.remoteModel.CreditModel
import com.lwinlwincho.domain.remoteModel.GenreModel
import com.lwinlwincho.domain.remoteModel.MovieDetailModel
import com.lwinlwincho.moviedbcompose.Loading
import com.lwinlwincho.moviedbcompose.R
import com.lwinlwincho.moviedbcompose.asyncImage
import com.lwinlwincho.moviedbcompose.home.HomeEvent
import com.lwinlwincho.moviedbcompose.toHourMinute
import com.lwinlwincho.moviedbcompose.ui.theme.MovieDBComposeTheme

@Composable
fun DetailScreen(
    onBack: () -> Unit,
) {
    val viewModel: MovieDetailViewModel = hiltViewModel()

    val uiState by viewModel.uiState.collectAsState()

    DetailContent(
        uiState = uiState,
        onEvent = { event ->
            when (event) {
                is HomeEvent.OnFavouriteEvent -> {
                    viewModel.toggleFavourite()
                }

                is HomeEvent.Back -> {
                    onBack()
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailContent(
    uiState: MovieDetailUiState,
    onEvent: (HomeEvent) -> Unit
) {

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Details",
                        style = MaterialTheme.typography.displayMedium
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { onEvent(HomeEvent.Back) },
                       /* modifier = Modifier
                            .clickable { onEvent(HomeEvent.Back) }*/
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
                actions = {
                    IconToggleButton(
                        checked = uiState.isFavourite,
                        onCheckedChange = { onEvent(HomeEvent.OnFavouriteEvent) }
                    ) {
                        Icon(
                            painter = painterResource(id = if (uiState.isFavourite) R.drawable.ic_save else R.drawable.ic_unsave),
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
        ) {

            if (uiState.loading) Loading()

            if (uiState.movieDetailModel.id.toInt() != 0) {
                DetailHeaderSession(movieDetailModel = uiState.movieDetailModel)
                DetailStarRate(voteAverage = uiState.movieDetailModel.voteAverage)
                DetailGenre(genres = uiState.movieDetailModel.genres)
                DetailLength(movieDetailModel = uiState.movieDetailModel)
                DetailDescriptionSession(overview = uiState.movieDetailModel.overview)
            }

            if (uiState.creditModel.cast.isNotEmpty()) {
                CastSession(uiState.creditModel.cast)
            }

            if (uiState.error.isNotEmpty()) {
                Toast.makeText(LocalContext.current, uiState.error, Toast.LENGTH_SHORT).show()
            }
        }
    }
}

@Composable
fun DetailHeaderSession(movieDetailModel: MovieDetailModel) {

    val isError by remember { mutableStateOf(false) }
    val placeholder = painterResource(id = R.drawable.dummy)
    val isLocalInspection = LocalInspectionMode.current

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        val (movieCover, moviePoster, movieName) = createRefs()

        Image(
            painter = if (isError.not() && !isLocalInspection) asyncImage(movieDetailModel.backdropPath) else placeholder,
            contentDescription = movieDetailModel.id.toString(),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .constrainAs(movieCover) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .aspectRatio(25f / 14f)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth(.3f)
                .aspectRatio(19f / 24f)
                .padding(start = 24.dp)
                .constrainAs(moviePoster) {
                    start.linkTo(parent.start)
                    bottom.linkTo(movieName.bottom)
                }
        ) {
            Image(
                painter = if (isError.not() && !isLocalInspection) asyncImage(movieDetailModel.posterPath) else placeholder,
                contentDescription = movieDetailModel.id.toString(),
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        Text(
            text = movieDetailModel.title,
            minLines = 2,
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier
                .constrainAs(movieName) {
                    top.linkTo(movieCover.bottom)
                    start.linkTo(moviePoster.end)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth(.7f)
                .padding(start = 12.dp, top = 12.dp)
        )
    }
}


@Composable
fun DetailStarRate(voteAverage: Double) {

    Row(
        modifier = Modifier.padding(top = 8.dp, start = 24.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_star_rate_24),
            contentDescription = null,
        )

        Text(
            text = stringResource(R.string.start_rate, voteAverage),
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier
                .wrapContentSize()
                .padding(start = 4.dp)
        )
    }
}

@Composable
fun DetailGenre(genres: List<GenreModel>) {
    LazyRow(
        contentPadding = PaddingValues(top = 16.dp, start = 24.dp, end = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(genres) {
            Text(
                text = it.name,
                modifier = Modifier
                    .wrapContentSize()
                    .clip(RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colorScheme.tertiary),
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}

@Composable
fun DetailLength(movieDetailModel: MovieDetailModel) {

    Row(
        modifier = Modifier
            .padding(start = 24.dp, top = 16.dp, end = 24.dp)
            .fillMaxWidth()
    ) {

        Column(
            modifier = Modifier.weight(.1f)
        ) {
            Text(
                text = "Length",
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.labelLarge
            )

            Text(
                text = movieDetailModel.runtime.toHourMinute(),
                modifier = Modifier.padding(top = 4.dp),
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.labelLarge
            )
        }

        Column(
            modifier = Modifier.weight(.1f)
        ) {
            Text(
                text = "Language",
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.labelLarge
            )

            Text(
                text = when (movieDetailModel.originalLanguage) {
                    "en" -> "English"
                    "ko" -> "Korea"
                    "ja" -> "Japan"
                    "fr" -> "France"
                    "ch" -> "China"
                    else -> movieDetailModel.originalLanguage
                },
                modifier = Modifier.padding(top = 4.dp),
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.labelLarge
            )
        }

        Column(
            modifier = Modifier.weight(.1f)
        ) {
            Text(
                text = "Rating",
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.labelLarge
            )

            Text(
                text = (movieDetailModel.voteAverage / 2).toString(),
                modifier = Modifier.padding(top = 4.dp),
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

@Composable
fun DetailDescriptionSession(overview: String) {
    Text(
        text = "Description",
        modifier = Modifier
            .wrapContentSize()
            .padding(top = 24.dp, start = 24.dp)
            .layoutId("tvDescription"),
        color = MaterialTheme.colorScheme.secondary,
        style = MaterialTheme.typography.headlineLarge
    )

    Text(
        text = overview,
        textAlign = TextAlign.Justify,
        modifier = Modifier
            .wrapContentSize()
            .padding(top = 8.dp, start = 24.dp, end = 24.dp)
            .layoutId("description"),
        color = MaterialTheme.colorScheme.secondary,
        style = MaterialTheme.typography.bodyLarge
    )
}

@Composable
fun CastSession(cast: List<CastModel>) {

    val isError by remember { mutableStateOf(false) }
    val placeholder = painterResource(id = R.drawable.dummy)
    val isLocalInspection = LocalInspectionMode.current

    Text(
        text = "Cast",
        modifier = Modifier
            .wrapContentSize()
            .padding(top = 24.dp, start = 24.dp),
        color = MaterialTheme.colorScheme.secondary,
        style = MaterialTheme.typography.headlineLarge
    )

    LazyRow(
        contentPadding = PaddingValues(end = 24.dp, start = 24.dp, top = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(cast) { item ->

            Column {
                /*Card(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .aspectRatio(19f / 24f)
                ) {*/
                Image(
                    painter = if (isError.not() && !isLocalInspection) asyncImage(item.profilePath) else placeholder,
                    contentDescription = item.id.toString(),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                Text(
                    text = item.name,
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(top = 8.dp),
                    minLines = 2,
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DetailContentPreview() {
    MovieDBComposeTheme {
        DetailContent(
            uiState = MovieDetailUiState(
                loading = false,
                movieDetailModel = MovieDetailModel(
                    "/feSiISwgEpVzR1v3zv2n2AU4ANJ.jpg",
                    listOf(
                        GenreModel(id = 28, name = "Action"),
                        GenreModel(id = 12, name = "Adventure"),
                        GenreModel(id = 878, name = "Science Fiction")

                    ),
                    609681,
                    "tt10676048",
                    "en",
                    "Spiderman No Way Home",
                    "Carol Danvers, aka Captain Marvel, has reclaimed her identity from the tyrannical Kree and taken revenge on the Supreme Intelligence. But unintended consequences see Carol shouldering the burden of a ",
                    "/tUtgLOESpCx7ue4BaeCTqp3vn1b.jpg",
                    "2023-11-08",
                    105,
                    "Spiderman No Way Home",
                    6.401
                ),
                creditModel = CreditModel(
                    609681,
                    listOf(
                        CastModel(1, "James", "John", "/tUtgLOESpCx7ue4BaeCTqp3vn1b.jpg"),
                        CastModel(2, "Jammy", "Johnny", "/tUtgLOESpCx7ue4BaeCTqp3vn1b.jpg"),
                        CastModel(3, "Jammy", "Johnny", "/tUtgLOESpCx7ue4BaeCTqp3vn1b.jpg"),
                        CastModel(4, "Jammy", "Johnny", "/tUtgLOESpCx7ue4BaeCTqp3vn1b.jpg")
                    )
                ),
                isFavourite = true,
            ),
            onEvent = {}
        )
    }
}