package com.example.myapplication.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.R
import com.example.myapplication.composables.NavBar
import com.example.myapplication.container.ContainerViewData
import com.example.myapplication.container.CreateViewData
import com.example.myapplication.container.MapViewModel
import com.example.myapplication.navigation.Routes
import com.example.myapplication.navigation.Routes.*
import com.example.myapplication.paging.PagingViewData

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(viewModel: PagingViewData, viewModel2: ContainerViewData, viewModel3: CreateViewData,  mapView: MapViewModel) {
    val navController = rememberNavController()
    val navigationsItems = listOf(
        Home,
        Register,
        List
    )
    Scaffold(
        bottomBar = { NavBar(navController = navController, items = navigationsItems) }
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(R.drawable.bg_iot),
                contentDescription = "Background",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize()
            )
            NavigationHost(navController, viewModel, viewModel2, viewModel3,  mapView)

        }
    }
}


@Composable
fun NavigationHost(navController: NavHostController, viewModel: PagingViewData, viewModel2: ContainerViewData, viewModel3: CreateViewData, mapView: MapViewModel){

    NavHost(
        navController = navController,
        startDestination = Home.route
    ) {
        composable(Home.route){
            HomeScreen(navController, viewModel3)
        }
        composable(Register.route){
            RegisterScreen(viewModel, navController)
        }
        composable(Routes.List.route){
            ListScreen()
        }
        composable(
            route = Routes.Detail.route,
            arguments = listOf(
                navArgument("deviceId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val deviceId = backStackEntry.arguments?.getString("deviceId")
            deviceId?.let {
                DetailScreen(deviceId, viewModel2)
            }
        }
        composable(
            route = Routes.Create.route,
            arguments = listOf(
                navArgument("latitud") {
                    type = NavType.FloatType
                    defaultValue = 0.0f // Puedes establecer un valor predeterminado si es necesario
                },
                navArgument("longitud") {
                    type = NavType.FloatType
                    defaultValue = 0.0f // Puedes establecer un valor predeterminado si es necesario
                },
            )
        ) {backStackEntry ->
            // Lógica para recuperar los argumentos y pasarlos a la pantalla CreateContainer
            val latitud = backStackEntry.arguments?.getFloat("latitud", 0.0f)
            val longitud = backStackEntry.arguments?.getFloat("longitud", 0.0f)

            Log.d("bbbbb", latitud.toString())
            CreateContainer(viewModel3, latitud, longitud)
        }

    }
}