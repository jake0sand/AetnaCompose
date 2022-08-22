package com.jakey.aetnacompose.di

import com.jakey.aetnacompose.data.remote.FlickrApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.time.Duration
import javax.inject.Singleton

/**
 * Dagger hilt depency injection allows me to put classes here that I only want to have to build
 * is one place. I can inject this API call anywhere in my app and Dagger Hilt makes sure that I
 * only have one active at any given time.
 */

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFlickrApi(): FlickrApi {
        val duration = Duration.ofSeconds(15L)
        return Retrofit.Builder()
            .baseUrl(FlickrApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .connectTimeout(duration)
                    .readTimeout(duration)
                    .writeTimeout(duration)
                    .addInterceptor(HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    }).build()
            )
            .build()
            .create(FlickrApi::class.java)
    }
}