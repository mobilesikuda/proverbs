package ru.sikuda.mobile.proverbs.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "catalogProverbs"
)
data class ProverbEntity(
    @ColumnInfo(name = "title") val title: String = "",
    @ColumnInfo(name = "description") val description: String = "",
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
)
