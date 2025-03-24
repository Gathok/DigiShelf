package de.malteans.digishelf.core.data.database

import androidx.room.RoomDatabaseConstructor

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object BookDatabaseConstructor: RoomDatabaseConstructor<BookDatabase> {
    override fun initialize(): BookDatabase
}