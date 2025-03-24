package de.malteans.digishelf.app

import androidx.compose.runtime.Composable

interface AppLocaleManager {
    fun getLocale(): String
}

@Composable
expect fun rememberAppLocale(): AppLang