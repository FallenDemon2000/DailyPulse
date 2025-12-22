//
//  AboutScreen.swift
//  iosApp
//
//  Created by Ângelo Mendonça on 20/12/2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI

struct AboutScreen:View {
    var body: some View {
        NavigationStack {
            AboutListView().navigationTitle ("About Device")
        }
    }
}

#Preview {
    AboutScreen()
}
