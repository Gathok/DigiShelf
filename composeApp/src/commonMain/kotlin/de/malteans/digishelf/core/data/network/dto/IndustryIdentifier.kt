package de.malteans.digishelf.core.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class IndustryIdentifier(
    val type: String,
    val identifier: String
)
