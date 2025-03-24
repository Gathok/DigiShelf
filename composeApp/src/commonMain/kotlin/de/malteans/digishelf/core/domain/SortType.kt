package de.malteans.digishelf.core.domain

enum class SortType {
    TITLE,
    AUTHOR,
    SERIES;

    val queryValue: String
        get() = when (this) {
            TITLE -> "title"
            AUTHOR -> "author"
            SERIES -> "series"
        }
}