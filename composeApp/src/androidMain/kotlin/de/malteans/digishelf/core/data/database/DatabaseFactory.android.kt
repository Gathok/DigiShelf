package de.malteans.digishelf.core.data.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

actual class DatabaseFactory(
    private val context: Context
) {
    actual fun create(): RoomDatabase.Builder<BookDatabase> {
        val appContext = context.applicationContext
        val dbFile = appContext.getDatabasePath(BookDatabase.DB_NAME)
        return Room.databaseBuilder<BookDatabase>(
            context = appContext,
            name = dbFile.absolutePath
        )
    }
}