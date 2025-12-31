package com.petros.efthymiou.dailypulse.sources.presentation

import com.petros.efthymiou.dailypulse.sources.domain.Source

abstract class SourcesState(
    val sources: List<Source> = emptyList(),
    val error: String? = null,
)

class SuccessSourcesState(sources: List<Source>) : SourcesState(sources = sources)
class ErrorSourcesState(error: String) : SourcesState(error = error)
class LoadingSourcesState() : SourcesState()
class EmptySourcesState() : SourcesState()
