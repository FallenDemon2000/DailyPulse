package com.petros.efthymiou.dailypulse.di

import com.petros.efthymiou.dailypulse.articles.presentation.ArticlesViewModel
import com.petros.efthymiou.dailypulse.sources.presentation.SourcesViewModel
import org.koin.core.KoinApplication
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin

fun initKoin(): KoinApplication =
    startKoin { modules(sharedKoinModules + databaseModule) }

class ArticlesInjector(): KoinComponent {
    val articlesViewModel: ArticlesViewModel by inject()
}

class SourcesInjector(): KoinComponent {
    val sourcesViewModel: SourcesViewModel by inject()
}
