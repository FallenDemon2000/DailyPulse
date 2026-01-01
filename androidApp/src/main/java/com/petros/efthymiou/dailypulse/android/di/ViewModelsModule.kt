package com.petros.efthymiou.dailypulse.android.di

import com.petros.efthymiou.dailypulse.articles.GetArticlesUseCase
import com.petros.efthymiou.dailypulse.articles.presentation.ArticlesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModules = module {
    viewModel { ArticlesViewModel(get<GetArticlesUseCase>()) }
}
