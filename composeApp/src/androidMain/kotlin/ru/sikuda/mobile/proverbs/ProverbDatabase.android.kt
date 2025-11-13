package ru.sikuda.mobile.proverbs

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.sikuda.mobile.proverbs.data.ProverbDatabase

fun getDatabaseBuilder(context: Context): RoomDatabase.Builder<ProverbDatabase> {
    val appContext = context.applicationContext
    val dbFile = appContext.getDatabasePath("proverbsData.db")
    return Room.databaseBuilder<ProverbDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    )
}