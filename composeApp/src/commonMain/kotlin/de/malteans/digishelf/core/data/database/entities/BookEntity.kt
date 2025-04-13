package de.malteans.digishelf.core.data.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "books",
    foreignKeys = [
        ForeignKey(
            entity = BookSeriesEntity::class,
            parentColumns = ["id"],
            childColumns = ["bookSeriesId"],
            onDelete = ForeignKey.SET_NULL,
        )
    ]
)
data class BookEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val title: String,
    val author: String,
    val isbn: String,
    val description: String = "",
    val onlineDescription: String? = null,
    val imageUrl: String = "",
    val pageCount: Int? = null,

    val price: Double? = null,
    val currency: String? = null,

    val bookSeriesId: Long? = null,

    val rating: Int? = null,
    val readStatus: Boolean = false,
    val readingTime: Int? = null,
    val possessionStatus: Boolean = false,
    val deletedSince: Long = 0,
)