package com.example.myapplication.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.container.CreateViewData
import com.example.myapplication.data.ContainerData

@Composable
fun CreateContainer(viewModel3: CreateViewData, latitud: Float?, longitud: Float?) {
    var codigo by remember { mutableStateOf("") }
    var latitud by remember { mutableStateOf(latitud.toString()) }
    var longitud by remember { mutableStateOf(longitud.toString()) }
    var descripcion by remember { mutableStateOf("") }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = codigo,
            onValueChange = { codigo = it },
            label = { Text("Código") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = latitud,
            onValueChange = { latitud = it },
            label = { Text("Latitud") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = longitud,
            onValueChange = { longitud = it },
            label = { Text("Longitud") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = { Text("Descripción") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val containerData = ContainerData(
                    device_id = codigo.toIntOrNull() ?: 0,
                    latitud = latitud.toDoubleOrNull() ?: 0.0,
                    longitud = longitud.toDoubleOrNull() ?: 0.0,
                    descripcion = descripcion
                )
                viewModel3.createDataFromApi(containerData)

            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Enviar")
        }
    }
}

