package com.petros.efthymiou.dailypulse.articles

import com.petros.efthymiou.dailypulse.BaseViewModel
import com.petros.efthymiou.dailypulse.articles.model.Article
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
