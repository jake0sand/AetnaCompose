package com.jakey.aetnacompose.data.remote.responses


data class ImageListDto(
    val description: String = "",
    val generator: String = "",
    val items: List<Item> = listOf(),
    val link: String = "",
    val modified: String = "",
    val title: String = ""
)