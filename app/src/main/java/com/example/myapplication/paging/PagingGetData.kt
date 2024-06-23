package com.example.myapplication.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.myapplication.data.ContainerData
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class PagingGetData @Inject constructor (private val api: PagingApiData) : PagingSource<Int, ContainerData>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ContainerData> {
        return try {
            val page = params.key ?: 0
            val perPage = params.loadSize
            val values = api.getData(page, perPage)

            LoadResult.Page(
                data = values,
                prevKey = if (page > 0) page - 1 else null,
                nextKey = if (values.isNotEmpty()) page + 1 else null
            )


        } catch (error: Throwable) {
            LoadResult.Error(error)
        }
    }
    override fun getRefreshKey(state: PagingState<Int, ContainerData>): Int? {
        return null
    }

}