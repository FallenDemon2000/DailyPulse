package com.petros.efthymiou.dailypulse.articles

import com.petros.efthymiou.dailypulse.articles.model.ArticleRaw

class ArticlesRepository(
    private val dataSource: ArticlesDataSource,
    private val service: ArticlesService,
) {
    suspend fun getArticles(forceFetch: Boolean): List<ArticleRaw> {
        if (forceFetch) {
            return fetchArticles()
        }

        val articlesDB = dataSource.getAllArticles()
            .also { article -> println("Articles: ${article.joinToString { it.title }}") }

        if (articlesDB.isEmpty()) {
            return fetchArticles()
        }
        return articlesDB
    }

    suspend fun fetchArticles(): List<ArticleRaw> =
        service.fetchArticles()
            .also { dataSource.insertArticles(it) }
}
