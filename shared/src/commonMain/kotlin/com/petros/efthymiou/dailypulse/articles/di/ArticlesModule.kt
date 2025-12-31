package com.petros.efthymiou.dailypulse.articles.di

import com.petros.efthymiou.dailypulse.NewsService
import com.petros.efthymiou.dailypulse.articles.GetArticlesUseCase
import com.petros.efthymiou.dailypulse.articles.data.ArticlesDataSource
import com.petros.efthymiou.dailypulse.articles.data.ArticlesRepository
import com.petros.efthymiou.dailypulse.articles.presentation.ArticlesViewModel
import org.koin.dsl.module
import petros.efthymiou.dailypulse.db.DailyPulseDatabase

val articlesModule = module {
    // Datasource
    single<ArticlesDataSource> { ArticlesDataSource(get<DailyPulseDatabase>()) }

    // Repository
    single<ArticlesRepository> {
        ArticlesRepository(
            dataSource = get<ArticlesDataSource>(),
            service = get<NewsService>(),
        )
    }

    // Domain
    single<GetArticlesUseCase> { GetArticlesUseCase(get<ArticlesRepository>()) }

    // Presentation
    single<ArticlesViewModel> { ArticlesViewModel(get<GetArticlesUseCase>()) }
}
