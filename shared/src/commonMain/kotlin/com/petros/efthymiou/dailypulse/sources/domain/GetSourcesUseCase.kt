package com.petros.efthymiou.dailypulse.sources.domain

import com.petros.efthymiou.dailypulse.sources.data.SourceRaw
import com.petros.efthymiou.dailypulse.sources.data.SourcesRepository

class GetSourcesUseCase(
    private val sourcesRepository: SourcesRepository,
) {

    suspend operator fun invoke(): List<Source> =
        sourcesRepository.getSources().toSourcesList()

    private fun List<SourceRaw>.toSourcesList(): List<Source> =
        map { it.toSource() }

    private fun SourceRaw.toSource(): Source =
        Source(
            id = id,
            name = name,
            description = description ?: "Click to find out more",
            origin = "$language-$country",
        )
}
