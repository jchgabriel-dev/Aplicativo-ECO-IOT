package com.example.myapplication.container


import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.RegisterData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContainerViewData @Inject constructor(
    private val api: ContainerApiData
) : ViewModel() {
    private val _registerData = MutableStateFlow<RegisterData?>(null)
    val registerData: StateFlow<RegisterData?> = _registerData
    private var job: Job? = null

    init {
        startPeriodicFetch("100")
    }
    fun startPeriodicFetch(deviceId: String) {
        job = CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                try {
                    val data = api.getHistoricData(deviceId)
                    _registerData.value = data // Actualiza los datos en el StateFlow
                } catch (e: Exception) {
                    // Maneja las excepciones según tus necesidades
                }
                delay(250) // Espera 100 segundos antes de la próxima verificación
            }
        }
    }

    // Detiene la verificación periódica cuando sea necesario (por ejemplo, al salir de la pantalla)
    fun stopPeriodicFetch() {
        job?.cancel()
    }
}