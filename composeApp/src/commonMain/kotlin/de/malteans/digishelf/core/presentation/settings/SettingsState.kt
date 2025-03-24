package de.malteans.digishelf.core.presentation.settings

import de.malteans.digishelf.core.domain.Book
import de.malteans.digishelf.core.domain.BookSeries
import de.malteans.digishelf.core.presentation.main.components.Screen

data class SettingsState(
    val curScreen: Screen = Screen.Settings,

    val trashIsEmpty: Boolean = true,
    val trashedBooks: List<Book> = emptyList(),

    val export: Boolean = false,
    val import: Boolean = false,
    val allBooks: List<Book>? = null,
    val allBookSeries: List<BookSeries>? = null,
    val isLoading: Boolean = false,
)
