//
//  ArticlesScreen.swift
//  iosApp
//
//  Created by Petros Efthymiou on 27/11/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

extension ArticlesScreen {
    
    @MainActor
    class ArticlesViewModelWrapper: ObservableObject {
        let articlesViewModel: ArticlesViewModel
        
        
        init() {
            articlesViewModel = ArticlesInjector().articlesViewModel
            articlesState = articlesViewModel.articlesState.value
        }
        
        @Published var articlesState: ArticlesState
        
        func startObserving() {
            Task {
                for await articlesS in articlesViewModel.articlesState {
                    self.articlesState = articlesS
                }
            }
        }
    }
}

struct ArticlesScreen: View {
    
    @ObservedObject private(set) var viewModel: ArticlesViewModelWrapper
    
    var body: some View {
        VStack {
            AppBar()
            
            let articlesState = viewModel.articlesState
            switch articlesState {
            case is LoadingArticlesState:
                Loader()
            case is ErrorArticlesState:
                ErrorMessage(message: articlesState.error ?? "Unknown Error")
            case is EmptyArticlesState:
                ErrorMessage(message: "Empty Article Found")
            default:
                ScrollView {
                    LazyVStack(spacing: 10) {
                        ForEach(articlesState.articles, id: \.self) { article in
                            ArticleItemView(article: article)
                        }
                    }
                }
            }
            
        }.onAppear{
            self.viewModel.startObserving()
        }
    }
}

private struct AppBar: View {
    var body: some View {
        Text("Articles")
            .font(.largeTitle)
            .fontWeight(.bold)
    }
}

private struct ArticleItemView: View {
    var article: Article
    
    var body: some View {
        VStack(alignment: .leading, spacing: 8) {
            AsyncImage(url: URL(string: article.imageUrl)) { phase in
                if phase.image != nil {
                    phase.image!
                        .resizable()
                        .aspectRatio(contentMode: .fit)
                } else if phase.error != nil {
                    Text("Image Load Error")
                } else {
                    ProgressView()
                }
            }
            Text(article.title)
                .font(.title)
                .fontWeight(.bold)
            Text(article.description)
            Text(article.date).frame(maxWidth: .infinity, alignment: .trailing).foregroundStyle(.gray)
        }
        .padding(16)
    }
}

private struct Loader: View {
    var body: some View {
        ProgressView()
    }
}

private struct ErrorMessage: View {
    var message: String
    
    var body: some View {
        Text(message)
            .font(.title)
    }
}
