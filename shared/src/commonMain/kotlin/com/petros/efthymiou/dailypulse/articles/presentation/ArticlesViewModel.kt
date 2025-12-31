package com.petros.efthymiou.dailypulse.articles.presentation

import com.petros.efthymiou.dailypulse.BaseViewModel
import com.petros.efthymiou.dailypulse.articles.GetArticlesUseCase
import com.petros.efthymiou.dailypulse.articles.domain.Article
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ArticlesViewModel(
    private val getArticlesUseCase: GetArticlesUseCase,
) : BaseViewModel() {
    private val _articlesState: MutableStateFlow<ArticlesState> =
        MutableStateFlow(LoadingArticlesState())

    val articlesState: StateFlow<ArticlesState> =
        _articlesState.asStateFlow()

    init {
        getArticles()
    }

    fun getArticles(forceFetch: Boolean = false) = scope.launch {
        _articlesState.update { LoadingArticlesState() }
        delay(1000L)
        _articlesState.update {
            when (val articles = getArticlesUseCase(forceFetch)) {
                emptyList<Article>() -> EmptyArticlesState()
                else -> SuccessArticlesState(articles = articles)
            }
        }
    }
}