package com.petros.efthymiou.dailypulse.articles

import com.petros.efthymiou.dailypulse.articles.model.ArticleRaw
import petros.efthymiou.dailypulse.db.DailyPulseDatabase

class ArticlesDataSource(private val database: DailyPulseDatabase) {

    fun getAllArticles(): List<ArticleRaw> =
        database.dailyPulseDatabaseQueries.selectAllArticles(::mapToArticleRaw).executeAsList()

    fun insertArticles(articles: List<ArticleRaw>) {
        database.dailyPulseDatabaseQueries.transaction {
            articles.forEach(::insertArticle)
        }
    }

    fun clearArticles() =
        database.dailyPulseDatabaseQueries.removeAllArticles()

    private fun mapToArticleRaw(title: String, desc: String?, date: String, url: String?): ArticleRaw =
        ArticleRaw(title, desc, date, url)

    private fun insertArticle(articleRaw: ArticleRaw) {
        database.dailyPulseDatabaseQueries.insertArticle(
            articleRaw.title,
            articleRaw.description,
            articleRaw.date,
            articleRaw.imageUrl
        )
    }
}
