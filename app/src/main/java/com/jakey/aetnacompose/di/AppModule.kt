package com.jakey.aetnacompose.di

import com.jakey.aetnacompose.data.remote.FlickrApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFlickrApi(): FlickrApi {
        return Retrofit.Builder()
            .baseUrl(FlickrApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(FlickrApi::class.java)
    }
}