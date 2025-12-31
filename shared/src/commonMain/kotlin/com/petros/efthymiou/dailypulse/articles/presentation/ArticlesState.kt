package com.petros.efthymiou.dailypulse.articles.presentation

import com.petros.efthymiou.dailypulse.articles.domain.Article

abstract class ArticlesState(
    val articles: List<Article> = emptyList(),
    val error: String? = null,
)

class SuccessArticlesState(articles: List<Article>) : ArticlesState(articles)
class ErrorArticlesState(error: String) : ArticlesState(error = error)
class LoadingArticlesState() : ArticlesState()
class EmptyArticlesState() : ArticlesState()
