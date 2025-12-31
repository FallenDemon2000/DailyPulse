package com.petros.efthymiou.dailypulse.sources.data

import petros.efthymiou.dailypulse.db.DailyPulseDatabase

class SourcesDataSource(private val database: DailyPulseDatabase) {

    fun getAllSources(): List<SourceRaw> =
        database.dailyPulseDatabaseQueries.selectAllSources(::mapToSourceRaw).executeAsList()

    fun insertSources(sources: List<SourceRaw>) {
        database.dailyPulseDatabaseQueries.transaction {
            sources.forEach(::insertSource)
        }
    }

    fun clearSources() =
        database.dailyPulseDatabaseQueries.removeAllSources()

    private fun mapToSourceRaw(id: String, name: String, description: String?, language: String, country: String): SourceRaw =
        SourceRaw(id, name, description, language, country)

    private fun insertSource(sourceRaw: SourceRaw) {
        database.dailyPulseDatabaseQueries.insertSource(
            sourceRaw.id,
            sourceRaw.name,
            sourceRaw.description,
            sourceRaw.language,
            sourceRaw.country,
        )
    }
}