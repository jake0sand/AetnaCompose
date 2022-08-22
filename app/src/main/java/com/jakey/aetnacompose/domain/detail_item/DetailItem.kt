package com.jakey.aetnacompose.domain.detail_item

/**
 * I'd normally have a model for each reponse but since I only had one response and passed over
 * the while object in navigation I thought it would be easier to just use the one model
 */

data class DetailItem(
    val title: String,
    val imageUrl: String,
    val author: String?,
    val imageHeight: String?,
    val imageWidth: String?,
    val description: String?
)
