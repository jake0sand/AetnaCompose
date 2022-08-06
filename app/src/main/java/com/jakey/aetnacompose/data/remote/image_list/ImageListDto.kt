package com.jakey.aetnacompose.data.remote.image_list


import com.squareup.moshi.Json

data class ImageListDto(
    val description: String = "",
    val generator: String = "",
    val items: List<Item> = listOf(),
    val link: String = "",
    val modified: String = "",
    val title: String = ""
)