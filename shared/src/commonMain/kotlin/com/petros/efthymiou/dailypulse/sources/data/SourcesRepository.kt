package com.petros.efthymiou.dailypulse.sources.data

import com.petros.efthymiou.dailypulse.NewsService

class SourcesRepository(
    private val dataSource: SourcesDataSource,
    private val service: NewsService,
) {
    suspend fun getSources(): List<SourceRaw> {
        val currentSources = dataSource.getAllSources().takeUnless { it.isEmpty() }
        println("Sources: ${currentSources?.joinToString { it.name }}")
        return currentSources ?: fetchSources()
    }

    suspend fun fetchSources(): List<SourceRaw> =
        service.fetchSources()
            .also { dataSource.insertSources(it) }
}
