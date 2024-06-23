package com.example.myapplication

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import com.example.myapplication.container.ContainerApiData
import com.example.myapplication.container.CreateApiData
import com.example.myapplication.data.ContainerData
import com.example.myapplication.paging.PagingApiData
import com.example.myapplication.paging.PagingGetData


import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@OptIn(ExperimentalPagingApi::class)
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providePagingSource(api: PagingApiData): PagingSource<Int, ContainerData> {
        return PagingGetData(api)
    }

    @Provides
    @Singleton
    fun providePagingApi(): PagingApiData {
        return Retrofit.Builder()
            .baseUrl(PagingApiData.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(PagingApiData::class.java)
    }

    @Provides
    @Singleton
    fun provideDataPager(api: PagingApiData, pagingSource: PagingSource<Int, ContainerData>): Pager<Int, ContainerData> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { pagingSource }
        )
    }

    @Provides
    @Singleton
    fun provideContainerApi(): ContainerApiData {
        return Retrofit.Builder()
            .baseUrl(ContainerApiData.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(ContainerApiData::class.java)
    }

    @Provides
    @Singleton
    fun provideCreateApi(): CreateApiData {
        return Retrofit.Builder()
            .baseUrl(CreateApiData.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(CreateApiData::class.java)
    }


}