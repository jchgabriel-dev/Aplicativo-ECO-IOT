package com.example.myapplication.container

import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng

class MapViewModel : ViewModel() {
    var cameraPosition = CameraPosition.Builder()
        .target(LatLng(-16.3988900, -71.5350000)) // Establece la posición inicial aquí
        .zoom(15f)
        .build()
}
