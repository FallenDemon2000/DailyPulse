package com.petros.efthymiou.dailypulse.articles

open class ArticleState(
    val articles:List<Article> = emptyList(),
    val loading: Boolean = false,
    val error: String? = null,
)

class LoadingArticleState()
    : ArticleState(emptyList(), false, null)

class SuccessArticleState(articles: List<Article>)
    : ArticleState(articles, false, null)

class ErrorArticleState(error: String)
    : ArticleState(emptyList(), false, error)

class EmptyArticleState()
    : ArticleState(emptyList(), false, null)
