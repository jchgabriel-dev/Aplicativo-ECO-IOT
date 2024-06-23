package com.example.myapplication.screens

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.SemanticsProperties.ImeAction
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.myapplication.container.CreateViewData
import com.example.myapplication.navigation.Routes
import com.example.myapplication.navigation.Routes.Home.title
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun HomeScreen(navController: NavHostController, viewModel3: CreateViewData) {
    Box(
        modifier = Modifier.fillMaxSize()
    ){

        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(height = 30.dp))
            Text(
                text = "EcoCuboX",
                fontSize = 35.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.height(height = 10.dp))

            MyMaps(navController, viewModel3)

        }

        Box(
            modifier = Modifier.fillMaxSize().padding(bottom = 70.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            FloatingActionButton(
                backgroundColor = Color.Black,
                onClick = {
                val route1 = "create/0.0/0.0" // Asegúrate de tener el ID correcto aquí
                navController.navigate(route1)

            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Crear contenedor",
                    tint = Color.White)
            }
        }

    }
}


@Composable
fun MyMaps(navController: NavHostController, viewModel3: CreateViewData) {

    val context = LocalContext.current // Agrega esta línea para obtener el contexto
    val initialCameraPosition = LatLng(-16.3988900,  -71.5350000)
    val defaultCameraPostion = CameraPosition.fromLatLngZoom(initialCameraPosition, 12.0f)
    val cameraPositionState = rememberCameraPositionState {
        position = defaultCameraPostion
    }

    val mapData by rememberUpdatedState(viewModel3.containerDataList)
    viewModel3.getDataFromApi()
    var lastLongClickPosition by remember { mutableStateOf(LatLng(0.0, 0.0)) }

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    LaunchedEffect(key1 = viewModel3) {
        viewModel3.getDataFromApi()
    }
    GoogleMap(
        modifier = Modifier.fillMaxSize().padding(bottom = 50.dp),
        cameraPositionState = cameraPositionState,
        onMapLongClick = { latLng ->
            lastLongClickPosition = latLng
            Log.d("bbbb", lastLongClickPosition.toString())
            val route = "create/${lastLongClickPosition.latitude.toFloat()}/${lastLongClickPosition.longitude.toFloat()}" // Asegúrate de tener el ID correcto aquí
            navController.navigate(route)
        }
    ) {
        mapData.value!!.forEach{ location ->
            Marker(

                title = location.descripcion,
                state = MarkerState(position = LatLng(location.latitud,location.longitud)),
                onInfoWindowClick = {
                        marker ->
                    //navController.navigate(Routes.Detail.route)
                }
            )
        }


    }

}
