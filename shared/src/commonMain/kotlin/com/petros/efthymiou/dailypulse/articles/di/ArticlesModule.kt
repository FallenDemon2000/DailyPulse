package com.petros.efthymiou.dailypulse.articles.di

import com.petros.efthymiou.dailypulse.articles.ArticlesService
import com.petros.efthymiou.dailypulse.articles.ArticlesViewModel
import com.petros.efthymiou.dailypulse.articles.GetArticlesUseCase
import io.ktor.client.HttpClient
import org.koin.dsl.module

val articlesModule = module {
    // Repository
    single<ArticlesService> { ArticlesService(get<HttpClient>()) }

    // Domain
    single<GetArticlesUseCase> { GetArticlesUseCase(get<ArticlesService>()) }

    // Presentation
    single<ArticlesViewModel> { ArticlesViewModel(get<GetArticlesUseCase>()) }
}
