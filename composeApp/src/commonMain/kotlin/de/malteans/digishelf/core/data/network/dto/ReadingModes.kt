package de.malteans.digishelf.core.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class ReadingModes(
    val text: Boolean,
    val image: Boolean
)
