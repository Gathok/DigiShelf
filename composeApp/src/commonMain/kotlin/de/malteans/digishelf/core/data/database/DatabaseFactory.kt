package de.malteans.digishelf.core.data.database

import androidx.room.RoomDatabase

expect class DatabaseFactory {
    fun create(): RoomDatabase.Builder<BookDatabase>
}