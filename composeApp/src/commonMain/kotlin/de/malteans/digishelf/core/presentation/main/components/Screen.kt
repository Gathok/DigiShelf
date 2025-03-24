package de.malteans.digishelf.core.presentation.main.components

import kotlinx.serialization.Serializable

enum class Screen {
    Overview,
    Add,
    Details,
    Scanner,
    Settings,
    Trash,
    SeriesOverview,
}

@Serializable
data object NavOverviewScreen

@Serializable
data class NavAddScreen (
    val isbn: String? = null
)

@Serializable
data class NavDetailsScreen (
    val bookId: Long
)

@Serializable
object NavScannerScreen

@Serializable
object NavSettingsScreen

@Serializable
object NavTrashScreen

@Serializable
object NavSeriesOverviewScreen