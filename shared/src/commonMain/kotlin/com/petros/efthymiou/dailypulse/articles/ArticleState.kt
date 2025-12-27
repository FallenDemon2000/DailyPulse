package com.petros.efthymiou.dailypulse.articles

abstract class ArticleState(
    val articles: List<Article> = emptyList(),
    val error: String? = null,
)

class SuccessArticleState(articles: List<Article>) : ArticleState(articles)
class ErrorArticleState(error: String) : ArticleState(error = error)
class LoadingArticleState() : ArticleState()
class EmptyArticleState() : ArticleState()
