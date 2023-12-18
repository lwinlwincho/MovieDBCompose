package com.lwinlwincho.moviedbcompose.detail

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.Alignment
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
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.layoutId
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.lwinlwincho.domain.model.CastModel
import com.lwinlwincho.domain.model.CreditModel
import com.lwinlwincho.domain.model.GenreModel
import com.lwinlwincho.domain.model.MovieDetailModel
import com.lwinlwincho.moviedbcompose.Loading
import com.lwinlwincho.moviedbcompose.R
import com.lwinlwincho.moviedbcompose.ui.theme.MovieDBComposeTheme
import com.lwinlwincho.network.IMAGE_URL

@Composable
fun DetailScreen() {
    val viewModel: MovieDetailViewModel = hiltViewModel()

    val detailUiState by viewModel.detailUiState.collectAsState()
    val castUiState by viewModel.castUiState.collectAsState()

    DetailContent(detailUiState = detailUiState, castUiState = castUiState)

    /*  viewModel.getMovieDetail(id)
      viewModel.getMovieCredit(id)*/
}

@Composable
fun DetailContent(detailUiState: MovieDetailUiState, castUiState: MovieDetailUiState) {

    var isLoading by remember { mutableStateOf(true) }
    var isError by remember { mutableStateOf(false) }
    val placeholder = painterResource(id = R.drawable.dummy)
    val isLocalInspection = LocalInspectionMode.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 20.dp, top = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (detailUiState.loading) {
            Loading()
        }

        if (detailUiState.movieDetailModel.id.toInt() != 0) {

            val coverImageLoader =
                rememberAsyncImagePainter(model = IMAGE_URL + detailUiState.movieDetailModel.backdropPath,
                    onState = { state ->
                        isLoading = state is AsyncImagePainter.State.Loading
                        isError = state is AsyncImagePainter.State.Error
                    }
                )

            val posterImageLoader =
                rememberAsyncImagePainter(model = IMAGE_URL + detailUiState.movieDetailModel.posterPath,
                    onState = { state ->
                        isLoading = state is AsyncImagePainter.State.Loading
                        isError = state is AsyncImagePainter.State.Error
                    }
                )

            val constraints = ConstraintSet {
                val imgBack = createRefFor("imgBack")
                val tvDetail = createRefFor("tvDetail")
                val imgSave = createRefFor("imgSave")
                val movieCover = createRefFor("movieCover")
                val moviePoster = createRefFor("moviePoster")
                val movieName = createRefFor("movieName")
                val imgStarRate = createRefFor("imgStarRate")
                val tvStarRate = createRefFor("tvStarRate")
                val genre = createRefFor("genre")
                val tvLength = createRefFor("tvLength")
                val tvLanguage = createRefFor("tvLanguage")
                val tvRating = createRefFor("tvRating")
                val length = createRefFor("length")
                val language = createRefFor("language")
                val rating = createRefFor("rating")
                val tvDescription = createRefFor("tvDescription")
                val description = createRefFor("description")

                constrain(imgBack) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                }
                constrain(tvDetail) {
                    start.linkTo(imgBack.end)
                    end.linkTo(imgSave.start)
                    top.linkTo(parent.top)
                }
                constrain(imgSave) {
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                }
                constrain(movieCover) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(tvDetail.bottom)
                }
                constrain(moviePoster) {
                    start.linkTo(parent.start)
                    bottom.linkTo(movieName.bottom)
                    end.linkTo(movieName.start)
                }
                constrain(movieName) {
                    top.linkTo(movieCover.bottom)
                    start.linkTo(moviePoster.end)
                    end.linkTo(parent.end)
                }
                constrain(imgStarRate) {
                    top.linkTo(moviePoster.bottom)
                    start.linkTo(parent.start)
                }
                constrain(tvStarRate) {
                    start.linkTo(imgStarRate.end)
                    top.linkTo(imgStarRate.top)
                    bottom.linkTo(imgStarRate.bottom)
                }
                constrain(genre) {
                    top.linkTo(imgStarRate.bottom)
                    start.linkTo(imgStarRate.start)
                }
                constrain(tvLength) {
                    start.linkTo(genre.start)
                    top.linkTo(genre.bottom)
                }
                constrain(tvLanguage) {
                    start.linkTo(tvLength.end)
                    top.linkTo(tvLength.top)
                    bottom.linkTo(tvLength.bottom)
                }
                constrain(tvRating) {
                    start.linkTo(tvLanguage.end)
                    top.linkTo(tvLength.top)
                    bottom.linkTo(tvLength.bottom)
                }
                constrain(length) {
                    start.linkTo(genre.start)
                    top.linkTo(tvLength.bottom)
                }
                constrain(language) {
                    start.linkTo(length.end)
                    top.linkTo(length.top)
                    bottom.linkTo(length.bottom)
                }
                constrain(rating) {
                    start.linkTo(language.end)
                    top.linkTo(length.top)
                    bottom.linkTo(length.bottom)
                }
                constrain(tvDescription) {
                    start.linkTo(imgStarRate.start)
                    top.linkTo(length.bottom)
                }
                constrain(description) {
                    start.linkTo(imgStarRate.start)
                    end.linkTo(parent.end)
                    top.linkTo(tvDescription.bottom)
                }
            }

            ConstraintLayout(
                constraintSet = constraints,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .verticalScroll(rememberScrollState())
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = null,
                    modifier = Modifier
                        .layoutId("imgBack")
                        .padding(start = 24.dp)
                )

                Text(
                    text = "Details",
                    modifier = Modifier.layoutId("tvDetail"),
                    style = MaterialTheme.typography.displayMedium
                )

                Image(
                    painter = painterResource(id = R.drawable.ic_save),
                    contentDescription = null,
                    modifier = Modifier
                        .layoutId("imgSave")
                        .padding(end = 24.dp)
                )

                Image(
                    painter = if (isError.not() && !isLocalInspection) coverImageLoader else placeholder,
                    contentDescription = detailUiState.movieDetailModel.id.toString(),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .aspectRatio(25f / 14f)
                        .padding(top = 16.dp)
                        .layoutId("movieCover")
                )

                Card(
                    modifier = Modifier
                        .fillMaxWidth(.3f)
                        .padding(start = 30.dp)
                        .aspectRatio(19f / 24f)
                        .layoutId("moviePoster")
                ) {
                    Image(
                        painter = if (isError.not() && !isLocalInspection) posterImageLoader else placeholder,
                        contentDescription = detailUiState.movieDetailModel.id.toString(),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Text(
                    text = detailUiState.movieDetailModel.title,
                    modifier = Modifier
                        .fillMaxWidth(.7f)
                        .padding(start = 12.dp, top = 12.dp, end = 30.dp)
                        .layoutId("movieName"),
                    minLines = 2,
                    style = MaterialTheme.typography.displayMedium
                )

                Image(
                    painter = painterResource(id = R.drawable.ic_star_rate_24),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(top = 8.dp, start = 20.dp)
                        .layoutId("imgStarRate")
                )

                Text(
                    text = stringResource(
                        R.string.start_rate, detailUiState.movieDetailModel.voteAverage
                    ),
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(start = 4.dp, top = 8.dp)
                        .layoutId("tvStarRate"),
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.labelLarge
                )

                LazyRow(
                    contentPadding = PaddingValues(top = 16.dp, start = 24.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.layoutId("genre"),
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

                Text(
                    text = "Length",
                    modifier = Modifier
                        .fillMaxWidth(.4f)
                        .padding(start = 24.dp, top = 16.dp)
                        .layoutId("tvLength"),
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.labelLarge
                )

                Text(
                    text = "Language",
                    modifier = Modifier
                        .fillMaxWidth(.4f)
                        .padding(top = 16.dp)
                        .layoutId("tvLanguage"),
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.labelLarge
                )

                Text(
                    text = "Rating",
                    modifier = Modifier
                        .fillMaxWidth(.3f)
                        .padding(top = 16.dp)
                        .layoutId("tvRating"),
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.labelLarge
                )

                Text(
                    text = detailUiState.movieDetailModel.runtime.toHourMinute(),
                    modifier = Modifier
                        .fillMaxWidth(.4f)
                        .padding(start = 24.dp, top = 4.dp)
                        .layoutId("length"),
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
                    modifier = Modifier
                        .fillMaxWidth(.4f)
                        .padding(top = 16.dp)
                        .layoutId("language"),
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.labelLarge
                )

                Text(
                    text = (detailUiState.movieDetailModel.voteAverage / 2).toString(),
                    modifier = Modifier
                        .fillMaxWidth(.3f)
                        .padding(top = 16.dp)
                        .layoutId("rating"),
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.labelLarge
                )

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


            /*ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .verticalScroll(rememberScrollState())
            ) {
                val (imgBack, tvDetail, imgSave, movieCover, moviePoster, movieName, imgStarRate, tvStarRate, genre, tvLength, tvLanguage, tvRating, length, language, rating, tvDescription, description) = createRefs()

                Image(painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = null,
                    modifier = Modifier.constrainAs(imgBack) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                    })

                Text(text = "Details", modifier = Modifier.constrainAs(tvDetail) {
                    start.linkTo(imgBack.end)
                    end.linkTo(imgSave.start)
                    top.linkTo(parent.top)
                })

                Image(painter = painterResource(id = R.drawable.ic_save),
                    contentDescription = null,
                    modifier = Modifier.constrainAs(imgSave) {
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                    })

                Image(painter = if (isError.not() && !isLocalInspection) coverImageLoader else placeholder,
                    contentDescription = detailUiState.movieDetailModel.id.toString(),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .constrainAs(movieCover) {
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            top.linkTo(tvDetail.bottom)
                        }
                        .aspectRatio(25f / 14f)
                        .padding(top = 16.dp))

                Card(modifier = Modifier
                    .constrainAs(moviePoster) {
                        start.linkTo(parent.start)
                        bottom.linkTo(movieName.bottom)
                    }
                    .padding(start = 30.dp, end = 12.dp)) {
                    Image(
                        painter = if (isError.not() && !isLocalInspection) posterImageLoader else placeholder,
                        contentDescription = detailUiState.movieDetailModel.id.toString(),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth(.3f)
                            .aspectRatio(19f / 24f)
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

                Image(painter = painterResource(id = R.drawable.ic_star_rate_24),
                    contentDescription = null,
                    modifier = Modifier
                        .constrainAs(imgStarRate) {
                            top.linkTo(moviePoster.bottom)
                            start.linkTo(parent.start)
                        }
                        .padding(start = 24.dp, top = 8.dp))

                Text(text = stringResource(
                    R.string.start_rate, detailUiState.movieDetailModel.voteAverage
                ),
                    modifier = Modifier
                        .wrapContentSize()
                        .constrainAs(tvStarRate) {
                            start.linkTo(imgStarRate.end)
                            top.linkTo(imgStarRate.top)
                            bottom.linkTo(imgStarRate.bottom)
                        }
                        .padding(start = 4.dp, top = 8.dp),
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.labelLarge)

                LazyRow(
                    modifier = Modifier.constrainAs(genre) {
                        top.linkTo(imgStarRate.bottom)
                        start.linkTo(imgStarRate.start)
                    },
                    contentPadding = PaddingValues(top = 16.dp, start = 24.dp),
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

                Text(text = "Length",
                    modifier = Modifier
                        .fillMaxWidth(.4f)
                        .constrainAs(tvLength) {
                            start.linkTo(genre.start)
                            top.linkTo(genre.bottom)
                        }
                        .padding(start = 24.dp, top = 16.dp),
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.labelLarge)

                Text(text = "Language",
                    modifier = Modifier
                        .fillMaxWidth(.4f)
                        .constrainAs(tvLanguage) {
                            start.linkTo(tvLength.end)
                            top.linkTo(tvLength.top)
                            bottom.linkTo(tvLength.bottom)
                        }
                        .padding(top = 16.dp),
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.labelLarge)

                Text(text = "Rating",
                    modifier = Modifier
                        .fillMaxWidth(.3f)
                        .constrainAs(tvRating) {
                            start.linkTo(tvLanguage.end)
                            top.linkTo(tvLength.top)
                            bottom.linkTo(tvLength.bottom)
                        }
                        .padding(top = 16.dp),
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.labelLarge)

                Text(text = detailUiState.movieDetailModel.runtime.toHourMinute(),
                    modifier = Modifier
                        .fillMaxWidth(.4f)
                        .constrainAs(length) {
                            start.linkTo(genre.start)
                            top.linkTo(tvLength.bottom)
                        }
                        .padding(start = 24.dp, top = 4.dp),
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.labelLarge)

                Text(text = when (detailUiState.movieDetailModel.originalLanguage) {
                    "en" -> "English"
                    "ko" -> "Korea"
                    "ja" -> "Japan"
                    "fr" -> "France"
                    "ch" -> "China"
                    else -> detailUiState.movieDetailModel.originalLanguage
                },
                    modifier = Modifier
                        .fillMaxWidth(.4f)
                        .constrainAs(language) {
                            start.linkTo(length.end)
                            top.linkTo(length.top)
                            bottom.linkTo(length.bottom)
                        }
                        .padding(top = 16.dp),
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.labelLarge)

                Text(text = (detailUiState.movieDetailModel.voteAverage / 2).toString(),
                    modifier = Modifier
                        .fillMaxWidth(.3f)
                        .constrainAs(rating) {
                            start.linkTo(language.end)
                            top.linkTo(length.top)
                            bottom.linkTo(length.bottom)
                        }
                        .padding(top = 16.dp),
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.labelLarge)

                Text(text = "Description",
                    modifier = Modifier
                        .wrapContentSize()
                        .constrainAs(tvDescription) {
                            start.linkTo(imgStarRate.start)
                            top.linkTo(length.bottom)
                        }
                        .padding(top = 24.dp, start = 24.dp),
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.headlineLarge)

                Text(text = detailUiState.movieDetailModel.overview,
                    textAlign = TextAlign.Justify,
                    modifier = Modifier
                        .wrapContentSize()
                        .constrainAs(description) {
                            start.linkTo(imgStarRate.start)
                            end.linkTo(parent.end)
                            top.linkTo(tvDescription.bottom)
                        }
                        .padding(top = 8.dp, start = 24.dp, end = 24.dp),
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.bodyLarge)
            }*/
        }

        if (castUiState.creditModel.cast.isNotEmpty()) {
            Text(
                text = "Cast",
                modifier = Modifier
                    .wrapContentSize()
                    .padding(top = 24.dp, start = 24.dp),
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.headlineLarge
            )

            LazyRow(
                contentPadding = PaddingValues(end = 24.dp, start = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(castUiState.creditModel.cast) { item ->
                    val castImageLoader =
                        rememberAsyncImagePainter(model = IMAGE_URL + item.profilePath,
                            onState = { state ->
                                isLoading = state is AsyncImagePainter.State.Loading
                                isError = state is AsyncImagePainter.State.Error
                            })

                    Column {
                        Card(
                            modifier = Modifier.clip(RoundedCornerShape(6.dp))
                        ) {
                            Image(
                                painter = if (isError.not() && !isLocalInspection) castImageLoader else placeholder,
                                contentDescription = item.id.toString(),
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxWidth(.4f)
                            )
                        }

                        Text(
                            text = item.name,
                            modifier = Modifier
                                .wrapContentSize()
                                .padding(top = 16.dp),
                            minLines = 2,
                            color = MaterialTheme.colorScheme.secondary,
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }
            }
        }

        if (detailUiState.error.isNotEmpty()) {
            Toast.makeText(
                LocalContext.current, detailUiState.error, Toast.LENGTH_SHORT
            ).show()
        }

    }
}


fun Long.toHourMinute(): String {
    val hours: Long = this / 60 //since both are ints, you get an int
    val minutes: Long = this % 60
    return "${hours}h ${minutes}m"
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