package de.malteans.digishelf.core.domain

data class BookSeries (
    val id: Long = 0L,
    val title: String,
    val description: String = "",
    val books: List<Book> = emptyList(),
)