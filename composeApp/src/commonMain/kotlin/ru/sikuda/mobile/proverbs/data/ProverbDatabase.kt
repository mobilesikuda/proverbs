package ru.sikuda.mobile.proverbs.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers

@Database(entities = [ProverbEntity::class], version = 1)
abstract class ProverbDatabase : RoomDatabase() {
    abstract fun proverbDao(): ProverbDao
}

fun getRoomDatabase(
    builder: RoomDatabase.Builder<ProverbDatabase>
): ProverbDatabase {
    return builder
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}