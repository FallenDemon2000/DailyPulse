package com.petros.efthymiou.dailypulse.articles

import com.petros.efthymiou.dailypulse.articles.model.ArticleRaw
import com.petros.efthymiou.dailypulse.articles.model.ArticlesResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse

class ArticlesService(
    private val httpClient: HttpClient,
) {

    private val country: String = "us"
    private val category: String = "business"
    private val apiKey: String = "7533f2a2cd6246d9980dfdb6e5cfa829"

    suspend fun fetchArticles(): List<ArticleRaw> =
        httpClient.getArticles().body<ArticlesResponse>().result

    private suspend fun HttpClient.getArticles(): HttpResponse =
        get(
            urlString = "https://newsapi.org/v2/top-headlines?" +
                    "country=$country&" +
                    "category=$category&" +
                    "apiKey=$apiKey",
        )
}
