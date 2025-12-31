package com.petros.efthymiou.dailypulse.sources.presentation

import com.petros.efthymiou.dailypulse.BaseViewModel
import com.petros.efthymiou.dailypulse.sources.domain.GetSourcesUseCase
import com.petros.efthymiou.dailypulse.articles.domain.Article
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SourcesViewModel(
    private val getSourcesUseCase: GetSourcesUseCase,
) : BaseViewModel() {
    private val _sourcesState: MutableStateFlow<SourcesState> =
        MutableStateFlow(LoadingSourcesState())

    val sourcesState: StateFlow<SourcesState> =
        _sourcesState.asStateFlow()

    init {
        getSources()
    }

    fun getSources() = scope.launch {
        _sourcesState.update { LoadingSourcesState() }
        delay(1000L)
        _sourcesState.update {
            when (val sources = getSourcesUseCase()) {
                emptyList<Article>() -> EmptySourcesState()
                else -> SuccessSourcesState(sources = sources)
            }
        }
    }
}
