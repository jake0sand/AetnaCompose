package com.jakey.aetnacompose.data.remote

import com.jakey.aetnacompose.data.remote.image_list.ImageListDto
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrApi {

    @GET("services/feeds/photos_public.gne?format=json&nojsoncallback=1")
    suspend fun getImages(
        @Query("tags") query: String
    ): ImageListDto

    companion object {
        /* Normally I wouldn't have such a large base url, but since I know the url I'm using
        I want to keep it simple.
         */

        const val BASE_URL = "https://api.flickr.com/"
    }
}