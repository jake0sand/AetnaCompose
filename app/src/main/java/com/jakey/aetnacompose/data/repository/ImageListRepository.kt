package com.jakey.aetnacompose.data.repository

import com.jakey.aetnacompose.data.remote.FlickrApi
import com.jakey.aetnacompose.domain.image.ListItem
import com.jakey.aetnacompose.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ImageListRepository @Inject constructor(
    private val api: FlickrApi
) {

     fun getImages(query: String): Flow<Resource<List<ListItem>>> = flow {
        try {
            emit(Resource.Loading())
            val result = api.getImages(query).items
            emit(Resource.Success(data = result.map { it.toListImage() }))
        } catch (e: IOException) {
            e.printStackTrace()
            emit(Resource.Error(
                message = e.message ?: "Check internet connection"
            ))
        } catch (e: HttpException) {
            e.printStackTrace()
            emit(Resource.Error(
                message = e.message ?: "Error fetching data"
            ))
        }
    }

}