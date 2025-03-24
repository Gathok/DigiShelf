package de.malteans.digishelf.app

import org.jetbrains.compose.resources.StringResource
import digishelf.composeapp.generated.resources.Res
import digishelf.composeapp.generated.resources.de
import digishelf.composeapp.generated.resources.en

enum class AppLang(
    val code: String,
    val StringRes: StringResource,
) {
    English("en", Res.string.en),
    German("de", Res.string.de),
}