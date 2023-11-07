package com.lwinlwincho.moviedbcompose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lwinlwincho.moviedbcompose.detail.DetailScreen
import com.lwinlwincho.moviedbcompose.home.HomeScreen
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.lwinlwincho.moviedbcompose.home.HomeViewModel

@Composable
fun MainScreen(
    navController: NavHostController = rememberNavController()
) {

    val viewModel: HomeViewModel = hiltViewModel()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val uiState by viewModel.nowShowingUIState.collectAsState()

    NavHost(
        navController = navController,
        startDestination = MovieScreenRoute.Home.route
    ) {
        var movieId : String? = null

        composable(route = MovieScreenRoute.Home.route) {
            HomeScreen {
                navController.navigate(MovieScreenRoute.Detail.route)
                movieId= it.toString()
            }
        }

        composable(
           // route = "${MovieScreenRoute.Detail.route}/${movieId}",
            route = MovieScreenRoute.Detail.route,
         //   arguments = listOf(navArgument("movieId") { type = NavType.StringType })
        ) { backStackEntry ->

            DetailScreen(movieId)
           // DetailScreen()

        }
    }

}

enum class MovieScreenRoute(val route: String) {
    Home(route = "home"),
    Detail(route = "detail"),
    Favourite(route = "favourite"),
    SeeMore(route = "seeMore")

}
