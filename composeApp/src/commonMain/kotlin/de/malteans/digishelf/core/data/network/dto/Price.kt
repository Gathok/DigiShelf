package de.malteans.digishelf.core.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class Price(
    val amount: Double,
    val currencyCode: String
)
