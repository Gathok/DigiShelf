package de.malteans.digishelf.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import de.malteans.digishelf.core.presentation.main.MainScreenRoot
import de.malteans.digishelf.theme.DigiShelfTheme

val LocalAppLocalization = compositionLocalOf {
    AppLang.English
}

@Composable
fun App() {
    val currentLanguage = rememberAppLocale()

    CompositionLocalProvider(LocalAppLocalization provides currentLanguage) {
        DigiShelfTheme {
            MainScreenRoot()
        }
    }
}