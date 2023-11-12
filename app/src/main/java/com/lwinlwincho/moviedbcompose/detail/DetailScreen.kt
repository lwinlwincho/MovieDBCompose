package com.lwinlwincho.moviedbcompose.detail

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.lwinlwincho.data.repository.MovieRepositoryImpl
import com.lwinlwincho.domain.model.GenreModel
import com.lwinlwincho.domain.model.MovieDetailModel
import com.lwinlwincho.domain.repository.MovieRepository
import com.lwinlwincho.moviedbcompose.Loading
import com.lwinlwincho.moviedbcompose.R
import com.lwinlwincho.network.IMAGE_URL

@Composable
fun DetailScreen(
    id: Int
) {
    val viewModel: MovieDetailViewModel = hiltViewModel()

    val detailUiState by viewModel.detailUiState.collectAsState()

    DetailContent(uiState = detailUiState)

    viewModel.getMovieDetail(id)
}

@Composable
fun DetailContent(uiState: MovieDetailUiState) {

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

        //  viewModel.getMovieDetail(movieId = id)
        when (uiState) {
            is MovieDetailUiState.Loading -> {
                Loading()
            }

            is MovieDetailUiState.SuccessMovieDetail -> {

                val imageLoader = rememberAsyncImagePainter(
                    model = IMAGE_URL + uiState.movieDetailModel.backdropPath,
                    onState = { state ->
                        isLoading = state is AsyncImagePainter.State.Loading
                        isError = state is AsyncImagePainter.State.Error
                    }
                )

                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    val (movieCover,
                        moviePoster,
                        movieName,
                        imgStarRate,
                        tvStarRate,
                        genre,
                        tvLength,
                        tvLanguage,
                        tvRating,
                        length,
                        language,
                        rating,
                        tvDescription,
                        description
                    ) = createRefs()

                    Image(
                        painter = if (isError.not() && !isLocalInspection) imageLoader else placeholder,
                        contentDescription = uiState.movieDetailModel.id.toString(),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .constrainAs(movieCover) {
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                top.linkTo(parent.top)
                            }
                            .aspectRatio(25f / 14f)
                            .padding(top = 16.dp)
                    )

                    Card(
                        modifier = Modifier
                            .constrainAs(moviePoster) {
                                start.linkTo(parent.start)
                                bottom.linkTo(movieName.bottom)
                            }
                            .padding(start = 30.dp, end = 12.dp)
                    ) {
                        Image(
                            painter = if (isError.not() && !isLocalInspection) imageLoader else placeholder,
                            contentDescription = uiState.movieDetailModel.id.toString(),
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth(.3f)
                                .aspectRatio(19f / 24f)
                        )
                    }

                    Text(
                        text = uiState.movieDetailModel.title,
                        modifier = Modifier
                            .constrainAs(movieName) {
                                top.linkTo(movieCover.bottom)
                                start.linkTo(moviePoster.end)
                                end.linkTo(parent.end)
                            }
                            .fillMaxWidth(.7f)
                            .padding(start = 12.dp, top = 12.dp),
                        minLines = 2,
                        style = MaterialTheme.typography.displayMedium
                    )

                    Image(
                        painter = painterResource(id = R.drawable.ic_star_rate_24),
                        contentDescription = null,
                        modifier = Modifier
                            .constrainAs(imgStarRate) {
                                top.linkTo(moviePoster.bottom)
                                start.linkTo(parent.start)
                            }
                            .padding(start = 24.dp, top = 8.dp)
                    )

                    Text(
                        text = stringResource(
                            R.string.start_rate,
                            uiState.movieDetailModel.voteAverage
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
                        style = MaterialTheme.typography.labelLarge
                    )

                    LazyRow(
                        modifier = Modifier.constrainAs(genre) {
                            top.linkTo(imgStarRate.bottom)
                            start.linkTo(imgStarRate.start)
                        },
                        contentPadding = PaddingValues(top = 16.dp, start = 24.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(uiState.movieDetailModel.genres) {
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
                            .constrainAs(tvLength) {
                                start.linkTo(genre.start)
                                top.linkTo(genre.bottom)
                            }
                            .padding(start = 24.dp, top = 16.dp),
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.labelLarge
                    )

                    Text(
                        text = "Language",
                        modifier = Modifier
                            .fillMaxWidth(.4f)
                            .constrainAs(tvLanguage) {
                                start.linkTo(tvLength.end)
                                top.linkTo(tvLength.top)
                                bottom.linkTo(tvLength.bottom)
                            }
                            .padding(top = 16.dp),
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.labelLarge
                    )

                    Text(
                        text = "Rating",
                        modifier = Modifier
                            .fillMaxWidth(.3f)
                            .constrainAs(tvRating) {
                                start.linkTo(tvLanguage.end)
                                top.linkTo(tvLength.top)
                                bottom.linkTo(tvLength.bottom)
                            }
                            .padding(top = 16.dp),
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.labelLarge
                    )

                    Text(
                        text = uiState.movieDetailModel.runtime.toHourMinute(),
                        modifier = Modifier
                            .fillMaxWidth(.4f)
                            .constrainAs(length) {
                                start.linkTo(genre.start)
                                top.linkTo(tvLength.bottom)
                            }
                            .padding(start = 24.dp, top = 4.dp),
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.labelLarge
                    )

                    Text(
                        text = when (uiState.movieDetailModel.originalLanguage) {
                            "en" -> "English"
                            "ko" -> "Korea"
                            "ja" -> "Japan"
                            "fr" -> "France"
                            "ch" -> "China"
                            else -> uiState.movieDetailModel.originalLanguage
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
                        style = MaterialTheme.typography.labelLarge
                    )

                    Text(
                        text = (uiState.movieDetailModel.voteAverage / 2).toString(),
                        modifier = Modifier
                            .fillMaxWidth(.3f)
                            .constrainAs(rating) {
                                start.linkTo(language.end)
                                top.linkTo(length.top)
                                bottom.linkTo(length.bottom)
                            }
                            .padding(top = 16.dp),
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.labelLarge
                    )

                    Text(
                        text = "Description",
                        modifier = Modifier
                            .wrapContentSize()
                            .constrainAs(tvDescription) {
                                start.linkTo(imgStarRate.start)
                                top.linkTo(length.bottom)
                            }
                            .padding(top = 24.dp, start = 24.dp),
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.headlineLarge
                    )

                    Text(
                        text = uiState.movieDetailModel.overview,
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
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            is MovieDetailUiState.Failure -> {
                Toast.makeText(
                    LocalContext.current,
                    uiState.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
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
    DetailContent(
        uiState = MovieDetailUiState.SuccessMovieDetail(
            MovieDetailModel(
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
                "Carol Danvers, aka Captain Marvel, has reclaimed her identity from the tyrannical Kree and taken revenge on the Supreme Intelligence. But unintended consequences see Carol shouldering the burden of a destabilized universe. When her duties send her to an anomalous wormhole linked to a Kree revolutionary, her powers become entangled with that of Jersey City super-fan Kamala Khan, aka Ms. Marvel, and Carol’s estranged niece, now S.A.B.E.R. astronaut Captain Monica Rambeau. Together, this unlikely trio must team up and learn to work in concert to save the universe.",
                "/tUtgLOESpCx7ue4BaeCTqp3vn1b.jpg",
                "2023-11-08",
                105,
                "Spiderman No Way Home",
                6.401
            )
        )
    )
}