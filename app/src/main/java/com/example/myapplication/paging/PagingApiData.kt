package com.example.myapplication.paging


import com.example.myapplication.data.ContainerData
import retrofit2.http.GET
import retrofit2.http.Query

interface PagingApiData {

    @GET("container")
    suspend fun getData(
        @Query("page") page: Int,
        @Query("per_page") pageCount: Int
    ): List<ContainerData>

    companion object {
        const val BASE_URL = "https://tgqetvnk03.execute-api.us-east-2.amazonaws.com/"
    }

}