package com.example.myapplication.container

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.ContainerData
import com.example.myapplication.data.RegisterData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateViewData @Inject constructor(
    private val api: CreateApiData
) : ViewModel() {

    private val _containerDataList = MutableLiveData<List<ContainerData>>()
    val containerDataList: LiveData<List<ContainerData>> = _containerDataList


   init {
       getDataFromApi()
   }
    fun createDataFromApi(container: ContainerData) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                api.putData(container)
            } catch (e: Exception) {
                // Manejar cualquier error que pueda ocurrir durante la solicitud PUT
            }
        }
    }

    fun getDataFromApi() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val data = api.getData()
                _containerDataList.postValue(data)
            } catch (e: Exception) {
                // Manejar cualquier error que pueda ocurrir durante la solicitud GET
            }
        }
    }
}