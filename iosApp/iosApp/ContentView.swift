import SwiftUI
import shared

struct ContentView: View {
    
    @State private var shouldOpenAbout: Bool = false
    
    var body: some View {
        let articleScreen = ArticlesScreen(viewModel: .init())
        NavigationStack {
            articleScreen.toolbar {
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
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}

