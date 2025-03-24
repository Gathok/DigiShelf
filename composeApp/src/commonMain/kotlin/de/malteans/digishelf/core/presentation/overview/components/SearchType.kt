package de.malteans.digishelf.core.presentation.overview.components

import de.malteans.digishelf.core.presentation.components.UiText
import digishelf.composeapp.generated.resources.Res
import digishelf.composeapp.generated.resources.author
import digishelf.composeapp.generated.resources.isbn
import digishelf.composeapp.generated.resources.series
import digishelf.composeapp.generated.resources.series_id
import digishelf.composeapp.generated.resources.title

enum class SearchType {
    TITLE,
    AUTHOR,
    ISBN,
    Series,
    SeriesId;

    val queryValue: String
        get() = when (this) {
            TITLE -> "title"
            AUTHOR -> "author"
            ISBN -> "isbn"
            Series -> "series"
            SeriesId -> "bookSeriesId"
        }

    val toUiText: UiText
        get() = when (this) {
            TITLE -> UiText.StringResourceId(Res.string.title)
            AUTHOR -> UiText.StringResourceId(Res.string.author)
            ISBN -> UiText.StringResourceId(Res.string.isbn)
            Series -> UiText.StringResourceId(Res.string.series)
            SeriesId -> UiText.StringResourceId(Res.string.series_id)
        }
}