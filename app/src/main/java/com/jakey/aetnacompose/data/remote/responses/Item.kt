package com.jakey.aetnacompose.data.remote.responses


import com.jakey.aetnacompose.data.domain.detail.DetailItem
import com.jakey.aetnacompose.domain.list.ListItem
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

    fun toDetailItem(): DetailItem {
        val stringBeforeWidth = "width=\\\""
        val stringAfterWidth = "\\\" "
        val stringBeforeHeight = "height=\\\""
        val stringAfterHeight = "\\\" "
        return DetailItem(
            image = media.m,
            title = title,
            description = description,
            imageWidth = description.substringAfter(stringAfterWidth).substringBefore(stringAfterWidth).toInt(),
            imageHeight = description.substringAfter(stringBeforeHeight).substringBefore(stringAfterHeight).toInt(),
            author = author
        )
    }
}