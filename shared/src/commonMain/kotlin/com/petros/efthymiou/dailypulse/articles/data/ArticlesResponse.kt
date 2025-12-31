package com.petros.efthymiou.dailypulse.articles.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArticlesResponse(
    @SerialName("status")
    val status: String,

    @SerialName("totalResults")
    val size: Int,

    @SerialName("articles")
    val result: List<ArticleRaw>,
)