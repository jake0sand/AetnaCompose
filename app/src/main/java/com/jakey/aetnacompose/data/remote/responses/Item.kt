package com.jakey.aetnacompose.data.remote.responses



import com.jakey.aetnacompose.domain.detail_item.DetailItem
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
    fun toListImage(): DetailItem {
        return DetailItem(
            title = title,
            imageUrl = media.m,
            author = author.substringAfter("(\"").substringBefore("\")"),
            imageHeight = description.substringAfter("height=\"").substringBefore("\""),
            imageWidth = description.substringAfter("width=\"").substringBefore("\""),
            /* I thought about parsing the last sentence that seemed like it was supposed to be the
             description, but a lot of pictures didn't have a final sentence and I didn't want
             blank descriptions */
            description = description
        )
    }
}