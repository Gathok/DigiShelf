package de.malteans.digishelf.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import de.malteans.digishelf.core.data.database.entities.BookEntity
import de.malteans.digishelf.core.data.database.entities.BookSeriesEntity

@Database(
    entities = [BookEntity::class, BookSeriesEntity::class],
    version = 1,
    exportSchema = false
)
abstract class BookDatabase: RoomDatabase() {

    abstract val bookDao: BookDao

    companion object {
        const val DB_NAME = "books.db"
    }
}