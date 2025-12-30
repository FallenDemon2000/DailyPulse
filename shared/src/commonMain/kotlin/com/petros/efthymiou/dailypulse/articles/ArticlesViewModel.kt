package com.petros.efthymiou.dailypulse.articles

import com.petros.efthymiou.dailypulse.BaseViewModel
import com.petros.efthymiou.dailypulse.articles.model.Article
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class ArticlesViewModel : BaseViewModel() {
    private val _articlesState: MutableStateFlow<ArticleState> =
        MutableStateFlow(LoadingArticleState())

    private val getArticlesUseCase: GetArticlesUseCase

    val articlesState: StateFlow<ArticleState> =
        _articlesState.asStateFlow()

    init {
        val httpClient = HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
        }

        val service = ArticlesService(httpClient)

        getArticlesUseCase = GetArticlesUseCase(service)

        getArticles()
    }

    private fun getArticles() = scope.launch {
        when (val articles = getArticlesUseCase()) {
            emptyList<Article>() -> EmptyArticleState()
            else -> SuccessArticleState(articles = articles)
        }
        _articlesState.update {
            SuccessArticleState(articles = getArticlesUseCase())
        }
    }
}
