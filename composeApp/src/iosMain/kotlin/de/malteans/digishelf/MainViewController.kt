package de.malteans.digishelf

import androidx.compose.ui.window.ComposeUIViewController
import de.malteans.digishelf.app.App
import de.malteans.digishelf.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) { App() }