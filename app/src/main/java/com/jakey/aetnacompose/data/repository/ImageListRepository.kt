package com.jakey.aetnacompose.data.repository

import com.jakey.aetnacompose.data.remote.FlickrApi
import com.jakey.aetnacompose.domain.detail_item.DetailItem
import com.jakey.aetnacompose.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

/**
 * This is the repository. It is used to be a "middle-man" between different data sources.
 * If I had a bigger more complicated app I could possibly have some local database functions in
 * here as well, or any other data sources I would want to "talk" to each other.
 * I left out Data Store because things were getting a bit lost "state-wise" so I decided to do
 * that directly in the ViewModel.
 */

class ImageListRepository @Inject constructor(
    private val api: FlickrApi,
) {
     suspend fun getImages(query: String): Flow<Resource<List<DetailItem>>> = flow {
        if (query.isNotBlank()) {
            try {
                emit(Resource.Loading())
                val result = api.getImages(query).items
                emit(Resource.Success(data = result.map { it.toListImage() }))

            } catch (e: IOException) {
                e.printStackTrace()
                emit(
                    Resource.Error(
                        message = e.message ?: "Check internet connection"
                    )
                )
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(
                    Resource.Error(
                        message = e.code().toString()
                    )
                )
            }
        }
    }

}