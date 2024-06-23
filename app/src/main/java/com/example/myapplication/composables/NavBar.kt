package com.example.myapplication.composables

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.myapplication.navigation.Routes
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Text
import androidx.compose.material.Icon
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun CurrentRoute(navController: NavController):String?{
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route

}

@Composable
fun NavBar(
    navController: NavController,
    items: List<Routes>
) {
    val currentRoute = CurrentRoute(navController)
    val isDetailScreen = currentRoute == Routes.Detail.route
    val isCreateScreen = currentRoute == Routes.Create.route

    BottomNavigation(
        backgroundColor = Color.Black,
    ) {
        items.forEach { screen ->
            BottomNavigationItem(
                unselectedContentColor = Color.White,
                selectedContentColor = Color.Green,
                label = { Text(screen.title!!) },
                icon = {
                    Icon(
                        painter = painterResource(id = screen.icon!!),
                        contentDescription = "icon",
                        tint = if ((currentRoute == screen.route)
                            || (isDetailScreen && screen == Routes.Register)
                            || (isCreateScreen && screen == Routes.Home))
                            Color.Green else Color.White
                    )
                },
                selected = currentRoute == screen.route
                        || (isDetailScreen && screen == Routes.Register)
                        || (isCreateScreen && screen == Routes.Home),
                onClick = {
                    if (screen.route != Routes.Home.route) {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                        }
                    } else if (currentRoute != Routes.Home.route) {
                        // Si intentas navegar a la pantalla de inicio desde una pantalla diferente,
                        // pop solo una pantalla superior.
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.findStartDestination().id - 1) {
                                saveState = true
                            }
                            launchSingleTop = true
                        }
                    }

                }
            )
        }
    }
}

