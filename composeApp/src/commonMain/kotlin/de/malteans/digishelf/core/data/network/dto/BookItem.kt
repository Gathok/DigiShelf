package de.malteans.digishelf.core.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class BookItem(
    val kind: String,
    val id: String,
    val etag: String,
    val selfLink: String,
    val volumeInfo: VolumeInfo,
    val saleInfo: SaleInfo,
    val accessInfo: AccessInfo,
    val searchInfo: SearchInfo? = null
)
