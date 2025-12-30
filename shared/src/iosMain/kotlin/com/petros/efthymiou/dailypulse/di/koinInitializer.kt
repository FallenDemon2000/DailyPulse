package com.petros.efthymiou.dailypulse.di

import com.petros.efthymiou.dailypulse.articles.ArticlesViewModel
import org.koin.core.KoinApplication
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin

fun initKoin(): KoinApplication =
    startKoin { modules(sharedKoinModules) }

class ArticlesInjector(): KoinComponent {
    val articlesViewModel: ArticlesViewModel by inject()
}
