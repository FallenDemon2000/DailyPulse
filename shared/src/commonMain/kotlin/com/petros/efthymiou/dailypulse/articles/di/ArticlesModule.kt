package com.petros.efthymiou.dailypulse.articles.di

import com.petros.efthymiou.dailypulse.articles.ArticlesDataSource
import com.petros.efthymiou.dailypulse.articles.ArticlesRepository
import com.petros.efthymiou.dailypulse.articles.ArticlesService
import com.petros.efthymiou.dailypulse.articles.ArticlesViewModel
import com.petros.efthymiou.dailypulse.articles.GetArticlesUseCase
import io.ktor.client.HttpClient
import org.koin.dsl.module
import petros.efthymiou.dailypulse.db.DailyPulseDatabase

val articlesModule = module {
    // Datasource
    single<ArticlesDataSource> { ArticlesDataSource(get<DailyPulseDatabase>()) }
    single<ArticlesService> { ArticlesService(get<HttpClient>()) }

    // Repository
    single<ArticlesRepository> {
        ArticlesRepository(
            dataSource = get<ArticlesDataSource>(),
            service = get<ArticlesService>(),
        )
    }

    // Domain
    single<GetArticlesUseCase> { GetArticlesUseCase(get<ArticlesRepository>()) }

    // Presentation
    single<ArticlesViewModel> { ArticlesViewModel(get<GetArticlesUseCase>()) }
}
