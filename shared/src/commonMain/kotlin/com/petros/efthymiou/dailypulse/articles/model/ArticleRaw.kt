package com.petros.efthymiou.dailypulse.articles.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArticleRaw(
    @SerialName("title")
    val title: String,

    @SerialName("description")
    val description: String?,

    @SerialName("publishedAt")
    val date: String,

    @SerialName("urlToImage")
    val imageUrl: String?,
)
