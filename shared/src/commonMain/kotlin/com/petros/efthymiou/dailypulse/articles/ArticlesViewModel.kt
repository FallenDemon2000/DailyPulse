package com.petros.efthymiou.dailypulse.articles

import com.petros.efthymiou.dailypulse.BaseViewModel
import com.petros.efthymiou.dailypulse.articles.model.Article
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ArticlesViewModel(
    private val getArticlesUseCase: GetArticlesUseCase,
) : BaseViewModel() {
    private val _articlesState: MutableStateFlow<ArticleState> =
        MutableStateFlow(LoadingArticleState())

    val articlesState: StateFlow<ArticleState> =
        _articlesState.asStateFlow()

    init {
        getArticles()
    }

    fun getArticles(forceFetch: Boolean = false) = scope.launch {
        _articlesState.update { LoadingArticleState() }
        delay(1000L)
        _articlesState.update {
            when (val articles = getArticlesUseCase(forceFetch)) {
                emptyList<Article>() -> EmptyArticleState()
                else -> SuccessArticleState(articles = articles)
            }
        }
    }
}
