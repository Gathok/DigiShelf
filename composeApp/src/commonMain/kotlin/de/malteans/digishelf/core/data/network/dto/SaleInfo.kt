package de.malteans.digishelf.core.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class SaleInfo(
    val country: String,
    val saleability: String,
    val isEbook: Boolean,
    val listPrice: Price? = null,
    val retailPrice: Price? = null,
    val buyLink: String? = null
)
