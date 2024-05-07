package com.lwinlwincho.moviedbcompose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
fun NavGraphController() {

    val navController = rememberNavController()

    var showBottomBar by rememberSaveable { mutableStateOf(true) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    showBottomBar = when (navBackStackEntry?.destination?.route) {
        "detail/{movieId}" -> false
        else -> true
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
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

@Composable
fun BottomBarSession(navController: NavHostController) {

    val bottomNavRouteList = listOf(
        BottomNavRoute.Home,
        BottomNavRoute.Favourite
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 0.dp
    ) {
        bottomNavRouteList.forEach { bottomNavRoute ->

            NavigationBarItem(
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = MaterialTheme.colorScheme.surface
                ),
                selected = currentDestination?.route == bottomNavRoute.route,
                icon = {
                    Icon(
                        painter = painterResource(id = getBottomNavIconForScreen(screen = bottomNavRoute.route)),
                        contentDescription = bottomNavRoute.route,
                        modifier = Modifier.size(26.dp)
                    )
                },
                label = {
                    Text(text = bottomNavRoute.route)
                },
                onClick = {
                    navController.navigate(bottomNavRoute.route) {
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
fun getBottomNavIconForScreen(screen: String): Int {
    return when (screen) {
        "Home" -> R.drawable.ic_home
        "Favourite" -> R.drawable.ic_favourite
        else -> {
            R.drawable.ic_home
        }
    }
}

sealed class BottomNavRoute(val route: String) {
    data object Home : BottomNavRoute("Home")
    data object Favourite : BottomNavRoute("Favourite")

}

enum class MovieScreenRoute(val route: String) {
    Home(route = "home"),
    Detail(route = "detail/{movieId}"),
    Favourite(route = "favourite"),
    SeeMore(route = "seeMore")
}

@Preview(showBackground = true)
@Composable
fun PreviewBottomBarSession() {
    MovieDBComposeTheme {
        Surface {
            BottomBarSession(navController = rememberNavController())
        }
    }
}


