package com.example.myapplication.container

import com.example.myapplication.data.RegisterData
import retrofit2.http.GET
import retrofit2.http.Path

interface ContainerApiData {
    @GET("historic/{device_id}")
    suspend fun getHistoricData(@Path("device_id") deviceId: String): RegisterData

    companion object {
        const val BASE_URL = "https://tgqetvnk03.execute-api.us-east-2.amazonaws.com/"
    }
}