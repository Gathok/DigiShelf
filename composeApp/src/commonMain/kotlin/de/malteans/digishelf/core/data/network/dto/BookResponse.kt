package de.malteans.digishelf.core.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class BookResponse(
    val kind: String,
    val totalItems: Int,
    val items: List<BookItem>? = null
)