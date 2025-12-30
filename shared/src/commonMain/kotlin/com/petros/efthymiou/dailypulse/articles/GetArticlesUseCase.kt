package com.petros.efthymiou.dailypulse.articles

import com.petros.efthymiou.dailypulse.articles.model.Article
import com.petros.efthymiou.dailypulse.articles.model.ArticleRaw
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.daysUntil
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayIn
import kotlin.math.abs

private const val DEFAULT_IMAGE_URL =
    "https://image.cnbcfm.com/api/v1/image/107326078-1698758530118-gettyimages-1765623456-wall26362_igj6ehhp.jpeg?v=1698758587&w=1920&h=1080"

class GetArticlesUseCase(
    private val articlesService: ArticlesService,
) {

    suspend operator fun invoke(): List<Article> =
        articlesService.fetchArticles().toArticlesList()

    private fun List<ArticleRaw>.toArticlesList(): List<Article> =
        map { it.toArticle() }

    private fun ArticleRaw.toArticle(): Article =
        Article(
            title = title,
            date = getDaysAgoString(date),
            description = description ?: "Click to find out more",
            imageUrl = imageUrl ?: DEFAULT_IMAGE_URL,
        )

    private fun getDaysAgoString(date: String): String {
        val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
        val articleDay = Instant.parse(date).toLocalDateTime(TimeZone.currentSystemDefault())
        val daysAgo = abs(today.daysUntil(articleDay.date))

        return when {
            daysAgo > 1 -> "$daysAgo days ago"
            daysAgo == 1 -> "Yesterday"
            else -> "Today"
        }
    }
}
