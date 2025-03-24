package de.malteans.digishelf.core.data.mappers

import de.malteans.digishelf.core.data.database.entities.BookSeriesEntity
import de.malteans.digishelf.core.domain.Book
import de.malteans.digishelf.core.domain.BookSeries

fun BookSeriesEntity.toDomain(booksList: List<Book>) : BookSeries {
    return BookSeries(
        id = this.id,
        title = this.title,
        description = this.description,
        books = booksList
    )
}

fun BookSeries.toEntity() : BookSeriesEntity {
    return BookSeriesEntity(
        id = this.id,
        title = this.title,
        description = this.description,
    )
}