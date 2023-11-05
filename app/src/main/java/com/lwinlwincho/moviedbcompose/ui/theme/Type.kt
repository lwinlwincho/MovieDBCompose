package com.lwinlwincho.moviedbcompose.ui.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.sp
import com.lwinlwincho.moviedbcompose.R
import com.lwinlwincho.moviedbcompose.ThemePreviewParameterProvider

// Set of Material typography styles to start with
val merriWeatherFont = FontFamily(
    Font(R.font.merriweather_bold, FontWeight.Bold)
)

val mulishFont = FontFamily(
    Font(R.font.mulish_blackitalic, FontWeight.Light),
    Font(R.font.mulish_bold, FontWeight.Bold),
    Font(R.font.mulish_regular, FontWeight.Normal)
)

val Typography = Typography(

    displayLarge = TextStyle(
        fontFamily = mulishFont,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        lineHeight = 28.sp
    ),
    displayMedium = TextStyle(
        fontFamily = mulishFont,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        lineHeight = 24.sp
    ),
    displaySmall = TextStyle(
        fontFamily = mulishFont,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 20.sp
    ),

    headlineLarge = TextStyle(
        fontFamily = merriWeatherFont,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 40.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = merriWeatherFont,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        lineHeight = 36.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = merriWeatherFont,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        lineHeight = 32.sp
    ),

    titleLarge = TextStyle(
        fontFamily = mulishFont,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    titleMedium = TextStyle(
        fontFamily = mulishFont,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        lineHeight = 24.sp
    ),
    titleSmall = TextStyle(
        fontFamily = mulishFont,
        fontWeight = FontWeight.Bold,
        fontSize = 10.sp,
        lineHeight = 20.sp
    ),

    bodyLarge = TextStyle(
        fontFamily = mulishFont,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 24.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = mulishFont,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp,
        lineHeight = 20.sp
    ),
    bodySmall = TextStyle(
        fontFamily = mulishFont,
        fontWeight = FontWeight.Normal,
        fontSize = 8.sp,
        lineHeight = 16.sp
    ),

    labelLarge = TextStyle(
        fontFamily = mulishFont,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        lineHeight = 20.sp
    ),
    labelMedium = TextStyle(
        fontFamily = mulishFont,
        fontWeight = FontWeight.Bold,
        fontSize = 10.sp,
        lineHeight = 16.sp
    ),
    labelSmall = TextStyle(
        fontFamily = mulishFont,
        fontWeight = FontWeight.Bold,
        fontSize = 8.sp,
        lineHeight = 16.sp
    )
)

@Preview
@Composable
fun PreviewTypography(
    @PreviewParameter(ThemePreviewParameterProvider::class) darkTheme: Boolean
) {

    MovieDBComposeTheme(darkTheme = darkTheme) {
        Surface {

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "headlinelarge",
                    style = MaterialTheme.typography.headlineLarge
                )
                Text(
                    text = "headlineMedium",
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = "headlinesmall",
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = "titlelinelarge",
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "titlelineMedium",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "titlelinesmall",
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    text = "bodylinelarge",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "bodylineMedium",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "bodylinesmall",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "labellinelarge",
                    style = MaterialTheme.typography.labelLarge
                )
                Text(
                    text = "labellineMedium",
                    style = MaterialTheme.typography.labelMedium
                )
                Text(
                    text = "labellinesmall",
                    style = MaterialTheme.typography.labelSmall
                )
            }

        }
    }
}