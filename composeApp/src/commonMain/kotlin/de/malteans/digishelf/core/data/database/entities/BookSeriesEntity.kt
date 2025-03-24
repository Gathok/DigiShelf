package de.malteans.digishelf.core.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "book_series"
)
data class BookSeriesEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val title: String,
    val description: String = "",
)