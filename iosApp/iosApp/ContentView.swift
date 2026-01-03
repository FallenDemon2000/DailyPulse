import SwiftUI
import shared

struct ContentView: View {
    
    @State private var shouldOpenAbout: Bool = false
    @State private var shouldOpenSources: Bool = false
    
    var body: some View {
        let articleScreen = ArticlesScreen(viewModel: .init())
        let SourcesScreen = SourcesScreen(viewModel: .init())
        NavigationStack {
            articleScreen.toolbar {
                ToolbarItem {
                    Button {
                        shouldOpenSources = true
                    } label: {
                        Label("Sources", systemImage: "list.bullet.rectangle").labelStyle(.titleAndIcon)
                    }.popover(isPresented: $shouldOpenSources) {
                        SourcesScreen
                    }
                }
                ToolbarItem {
                    Button {
                        shouldOpenAbout = true
                    } label: {
                        Label("About", systemImage: "info.circle").labelStyle(.titleAndIcon)
                    }.popover(isPresented: $shouldOpenAbout) {
                        AboutScreen()
                    }
                }
            }
        }.refreshable {
            articleScreen.viewModel.articlesViewModel.getArticles(forceFetch: true)
            SourcesScreen.viewModel.sourcesViewModel.getSources()
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}

