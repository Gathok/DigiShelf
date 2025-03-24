package de.malteans.digishelf.core.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class Pdf(
    val isAvailable: Boolean,
    val acsTokenLink: String? = null
)
