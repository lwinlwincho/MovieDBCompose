package com.lwinlwincho.moviedbcompose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.lwinlwincho.network.IMAGE_URL

fun Long.toHourMinute(): String {
    val hours: Long = this / 60 //since both are ints, you get an int
    val minutes: Long = this % 60
    return "${hours}h ${minutes}m"
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
