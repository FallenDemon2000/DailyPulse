//
//  SourcesScreen.swift
//  iosApp
//
//  Created by Ângelo Mendonça on 30/12/2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import shared

extension SourcesScreen {
    
    @MainActor
    class SourcesViewModelWrapper: ObservableObject {
        let sourcesViewModel: SourcesViewModel
        
        
        init() {
            sourcesViewModel = SourcesInjector().sourcesViewModel
            sourcesState = sourcesViewModel.sourcesState.value
        }
        
        @Published var sourcesState: SourcesState
        
        func startObserving() {
            Task {
                for await sourcesS in sourcesViewModel.sourcesState {
                    self.sourcesState = sourcesS
                }
            }
        }
    }
}

struct SourcesScreen: View {
    @Environment(\.dismiss)
    private var dismiss
    
    @ObservedObject private(set) var viewModel: SourcesScreen.SourcesViewModelWrapper
    
    var body: some View {
        NavigationStack {
            VStack {
                
                let sourcesState = viewModel.sourcesState
                switch sourcesState {
                case is LoadingSourcesState:
                    Loader()
                case is ErrorSourcesState:
                    ErrorMessage(message: sourcesState.error ?? "Unknown Error")
                case is EmptySourcesState:
                    ErrorMessage(message: "Empty Source Found")
                default:
                    ScrollView {
                        LazyVStack(spacing: 10) {
                            ForEach(viewModel.sourcesState.sources, id: \.self) { source in
                                SourceItemView(name: source.name, description: source.description, origin: source.origin)
                            }
                        }
                    }
                }
            }.onAppear{
                self.viewModel.startObserving()
            }
            .navigationTitle("Sources")
            .toolbar {
                ToolbarItem(placement: .primaryAction) {
                    Button {
                        dismiss()
                    } label: {
                        Text("Done")
                            .bold()
                    }
                }
            }
        }
    }
}

struct SourceItemView: View {
    let name: String
    let description: String
    let origin: String
    
    var body: some View {
        VStack(alignment: .leading, spacing: 8) {
            Text(name)
                .font(.title)
                .fontWeight(.bold)
            Text(description)
            Text(origin).frame(maxWidth: .infinity, alignment: .trailing).foregroundStyle(.gray)
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
