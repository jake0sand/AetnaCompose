package com.jakey.aetnacompose.data.remote.image_list


import com.jakey.aetnacompose.domain.image.ListItem
import com.squareup.moshi.Json

data class Item(
    val author: String = "",
    @Json(name = "author_id")
    val authorId: String = "",
    @Json(name = "date_taken")
    val dateTaken: String = "",
    val description: String = "",
    val link: String = "",
    val media: Media = Media(),
    val published: String = "",
    val tags: String = "",
    val title: String = ""
) {
    fun toListImage(): ListItem {
        return ListItem(
            id = link.dropLast(1).split('/').last().toLong(),
            title = title,
            imageUrl = media.m
        )
    }
}