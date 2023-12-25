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
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.lwinlwincho.domain.remoteModel.CastModel
import com.lwinlwincho.domain.remoteModel.CreditModel
import com.lwinlwincho.domain.remoteModel.GenreModel
import com.lwinlwincho.domain.remoteModel.MovieDetailModel
import com.lwinlwincho.moviedbcompose.Loading
import com.lwinlwincho.moviedbcompose.R
import com.lwinlwincho.moviedbcompose.toHourMinute
import com.lwinlwincho.moviedbcompose.ui.theme.MovieDBComposeTheme
import com.lwinlwincho.network.IMAGE_URL

@Composable
fun DetailScreen() {
    val viewModel: MovieDetailViewModel = hiltViewModel()

    val detailUiState by viewModel.detailUiState.collectAsState()
    val castUiState by viewModel.castUiState.collectAsState()

    DetailContent(detailUiState = detailUiState, castUiState = castUiState)

}

@Composable
fun DetailContent(detailUiState: MovieDetailUiState, castUiState: MovieDetailUiState) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 20.dp, top = 20.dp)
            .verticalScroll(rememberScrollState()),
    ) {

        if (detailUiState.loading) {
            Loading()
        }

        if (detailUiState.movieDetailModel.id.toInt() != 0) {

            DetailHeaderSession(detailUiState)
            DetailStarRate(detailUiState)
            DetailGenre(detailUiState)
            DetailLength(detailUiState)
            DetailDescriptionSession(detailUiState)
        }

        if (castUiState.creditModel.cast.isNotEmpty()) {
            CastSession(castUiState)
        }

        if (detailUiState.error.isNotEmpty()) {
            Toast.makeText(
                LocalContext.current, detailUiState.error, Toast.LENGTH_SHORT
            ).show()
        }

    }
}

@Composable
fun DetailHeaderSession(detailUiState: MovieDetailUiState) {

    var isError by remember { mutableStateOf(false) }
    val placeholder = painterResource(id = R.drawable.dummy)
    val isLocalInspection = LocalInspectionMode.current

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {

        val (imgBack, tvDetail, imgSave, movieCover, moviePoster, movieName) = createRefs()

        Image(
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = null,
            modifier = Modifier
                .constrainAs(imgBack) {
                    start.linkTo(parent.start)
                }
                .padding(start = 24.dp)
        )

        Text(
            text = "Details",
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier.constrainAs(tvDetail) {
                start.linkTo(imgBack.end)
                end.linkTo(imgSave.start)
                top.linkTo(parent.top)
            }
        )

        Image(
            painter = painterResource(id = R.drawable.ic_save),
            contentDescription = null,
            modifier = Modifier
                .constrainAs(imgSave) {
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                }
                .padding(end = 24.dp)
        )

        Image(
            painter = if (isError.not() && !isLocalInspection) asyncImage(detailUiState.movieDetailModel.backdropPath) else placeholder,
            contentDescription = detailUiState.movieDetailModel.id.toString(),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .constrainAs(movieCover) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(tvDetail.bottom)
                }
                .aspectRatio(25f / 14f)
                .padding(top = 16.dp)
        )

        Card(modifier = Modifier
            .constrainAs(moviePoster) {
                start.linkTo(parent.start)
                bottom.linkTo(movieName.bottom)
            }
            .fillMaxWidth(.3f)
            .aspectRatio(19f / 24f)
            .padding(start = 24.dp)
        ) {
            Image(
                painter = if (isError.not() && !isLocalInspection) asyncImage(detailUiState.movieDetailModel.posterPath) else placeholder,
                contentDescription = detailUiState.movieDetailModel.id.toString(),
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        Text(text = detailUiState.movieDetailModel.title,
            modifier = Modifier
                .constrainAs(movieName) {
                    top.linkTo(movieCover.bottom)
                    start.linkTo(moviePoster.end)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth(.7f)
                .padding(start = 12.dp, top = 12.dp),
            minLines = 2,
            style = MaterialTheme.typography.displayMedium)
    }
}

@Composable
fun DetailStarRate(detailUiState: MovieDetailUiState) {

    Row(
        modifier = Modifier
            .padding(top = 8.dp, start = 24.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_star_rate_24),
            contentDescription = null,
        )

        Text(
            text = stringResource(R.string.start_rate, detailUiState.movieDetailModel.voteAverage),
            modifier = Modifier
                .wrapContentSize()
                .padding(start = 4.dp),
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Composable
fun DetailGenre(detailUiState: MovieDetailUiState) {
    LazyRow(
        contentPadding = PaddingValues(top = 16.dp, start = 24.dp, end = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(detailUiState.movieDetailModel.genres) {
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
fun DetailLength(detailUiState: MovieDetailUiState) {

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
                text = detailUiState.movieDetailModel.runtime.toHourMinute(),
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
                text = when (detailUiState.movieDetailModel.originalLanguage) {
                    "en" -> "English"
                    "ko" -> "Korea"
                    "ja" -> "Japan"
                    "fr" -> "France"
                    "ch" -> "China"
                    else -> detailUiState.movieDetailModel.originalLanguage
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
                text = (detailUiState.movieDetailModel.voteAverage / 2).toString(),
                modifier = Modifier.padding(top = 4.dp),
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

@Composable
fun DetailDescriptionSession(detailUiState: MovieDetailUiState) {
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
        text = detailUiState.movieDetailModel.overview,
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
fun CastSession(castUiState: MovieDetailUiState) {

    var isError by remember { mutableStateOf(false) }
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
        items(castUiState.creditModel.cast) { item ->

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

@Composable
fun asyncImage(posterPath: String): AsyncImagePainter {

    var isLoading by remember { mutableStateOf(true) }
    var isError by remember { mutableStateOf(false) }

    val imageLoader =
        rememberAsyncImagePainter(model = IMAGE_URL + posterPath,
            onState = { state ->
                isLoading = state is AsyncImagePainter.State.Loading
                isError = state is AsyncImagePainter.State.Error
            }
        )
    return imageLoader
}

@Preview(showBackground = true)
@Composable
fun DetailContentPreview() {
    MovieDBComposeTheme {
        DetailContent(
            detailUiState = MovieDetailUiState(
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
                )
            ),
            castUiState = MovieDetailUiState(
                loading = false,
                creditModel = CreditModel(
                    609681,
                    listOf(
                        CastModel(1, "James", "John", "/tUtgLOESpCx7ue4BaeCTqp3vn1b.jpg"),
                        CastModel(2, "Jammy", "Johnny", "/tUtgLOESpCx7ue4BaeCTqp3vn1b.jpg"),
                        CastModel(3, "Jammy", "Johnny", "/tUtgLOESpCx7ue4BaeCTqp3vn1b.jpg"),
                        CastModel(4, "Jammy", "Johnny", "/tUtgLOESpCx7ue4BaeCTqp3vn1b.jpg")
                    )
                )
            )
        )
    }
}