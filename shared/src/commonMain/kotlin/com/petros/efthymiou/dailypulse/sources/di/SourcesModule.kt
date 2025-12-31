package com.petros.efthymiou.dailypulse.sources.di

import com.petros.efthymiou.dailypulse.NewsService
import com.petros.efthymiou.dailypulse.sources.domain.GetSourcesUseCase
import com.petros.efthymiou.dailypulse.sources.data.SourcesDataSource
import com.petros.efthymiou.dailypulse.sources.data.SourcesRepository
import com.petros.efthymiou.dailypulse.sources.presentation.SourcesViewModel
import org.koin.dsl.module
import petros.efthymiou.dailypulse.db.DailyPulseDatabase

val sourcesModule = module {
    // Datasource
    single<SourcesDataSource> { SourcesDataSource(get<DailyPulseDatabase>()) }

    // Repository
    single<SourcesRepository> {
        SourcesRepository(
            dataSource = get<SourcesDataSource>(),
            service = get<NewsService>(),
        )
    }

    // Domain
    single<GetSourcesUseCase> { GetSourcesUseCase(get<SourcesRepository>()) }

    // Presentation
    single<SourcesViewModel> { SourcesViewModel(get<GetSourcesUseCase>()) }
}
