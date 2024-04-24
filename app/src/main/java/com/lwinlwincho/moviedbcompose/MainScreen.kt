package com.lwinlwincho.moviedbcompose

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.lwinlwincho.moviedbcompose.detail.DetailScreen
import com.lwinlwincho.moviedbcompose.favourite.FavouriteScreen
import com.lwinlwincho.moviedbcompose.home.HomeScreen
import com.lwinlwincho.moviedbcompose.ui.theme.MovieDBComposeTheme

@Composable
fun MainScreen() {

    val navController = rememberNavController()

    var showBottomBar by rememberSaveable { mutableStateOf(true) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    showBottomBar = when (navBackStackEntry?.destination?.route) {
        "detail/{movieId}" -> false
        else -> true
    }

    MovieDBComposeTheme {
        Scaffold(
            bottomBar = {
                if (showBottomBar)
                    BottomBarSession(navController = navController)
            }
        ) { innerPadding ->

            NavHost(
                navController = navController,
                startDestination = MovieScreenRoute.Home.route,
                modifier = Modifier.padding(paddingValues = innerPadding)
            ) {
                composable(route = MovieScreenRoute.Home.route) {
                    HomeScreen(navController = navController)
                }

                composable(route = MovieScreenRoute.Favourite.route) {
                    FavouriteScreen(navController = navController)
                }

                composable(
                    route = MovieScreenRoute.Detail.route,
                    arguments = listOf(navArgument("movieId") { type = NavType.IntType })
                ) {
                    DetailScreen(onBack = { navController.navigateUp() })
                }
            }
        }
    }

    /* NavHost(
         navController = navController,
         startDestination = MovieScreenRoute.Home.route
     ) {

         composable(route = MovieScreenRoute.Home.route) {
             HomeScreen(navController)
         }

         composable(
             route = MovieScreenRoute.Detail.route,
             arguments = listOf(navArgument("movieId") { type = NavType.IntType })
         ) { backStackEntry ->
             val id = backStackEntry.arguments?.getInt("movieId")
             if (id != null) {
                 DetailScreen(id)
             }
         }
     }*/
}

@Composable
fun BottomBarSession(navController: NavHostController) {

    val bottomNavRouteList = listOf(
        BottomNavRoute.Home,
        BottomNavRoute.Favourite
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar {
        bottomNavRouteList.forEach { screen ->
            NavigationBarItem(
                selected = currentDestination?.route == screen.route,
                icon = {
                    Icon(
                        imageVector = getBottomNavIconForScreen(screen = screen.route),
                        contentDescription = screen.route
                    )
                },
                label = { Text(text = screen.route) },
                onClick = {
                    navController.navigate(screen.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Composable
fun getBottomNavIconForScreen(screen: String): ImageVector {
    return when (screen) {
        "home" -> Icons.Filled.Home
        "favourite" -> Icons.Filled.Favorite
        else -> {
            Icons.Filled.Home
        }
    }
}

sealed class BottomNavRoute(val route: String) {
    data object Home : BottomNavRoute("home")
    data object Favourite : BottomNavRoute("favourite")

}

enum class MovieScreenRoute(val route: String) {
    Home(route = "home"),
    Detail(route = "detail/{movieId}"),
    Favourite(route = "favourite"),
    SeeMore(route = "seeMore")
}
