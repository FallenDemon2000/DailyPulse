package com.petros.efthymiou.dailypulse

import com.petros.efthymiou.dailypulse.articles.data.ArticleRaw
import com.petros.efthymiou.dailypulse.articles.data.ArticlesResponse
import com.petros.efthymiou.dailypulse.sources.data.SourceRaw
import com.petros.efthymiou.dailypulse.sources.data.SourcesResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse

class NewsService(
    private val httpClient: HttpClient,
) {

    private val country: String = "us"
    private val category: String = "business"
    private val apiKey: String = "7533f2a2cd6246d9980dfdb6e5cfa829"

    suspend fun fetchArticles(): List<ArticleRaw> =
        httpClient.getArticles().body<ArticlesResponse>().result

    suspend fun fetchSources(): List<SourceRaw> =
        httpClient.getSources().body<SourcesResponse>().sources

    private suspend fun HttpClient.getArticles(): HttpResponse =
        get(
            urlString = "https://newsapi.org/v2/top-headlines?" +
                    "country=$country&" +
                    "category=$category&" +
                    "apiKey=$apiKey",
        )

    private suspend fun HttpClient.getSources(): HttpResponse =
        get(
            urlString = "https://newsapi.org/v2/top-headlines/sources?" +
                    "country=$country&" +
                    "category=$category&" +
                    "apiKey=$apiKey",
        )
}
