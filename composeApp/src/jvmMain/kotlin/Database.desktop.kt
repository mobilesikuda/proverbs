import androidx.room.Room
import androidx.room.RoomDatabase
import ru.sikuda.mobile.proverbs.data.ProverbDatabase
import java.io.File

fun getDatabaseBuilder(): RoomDatabase.Builder<ProverbDatabase> {
    val dbFile = File(System.getProperty("java.io.tmpdir"), "proverbDatabase.db")
    return Room.databaseBuilder<ProverbDatabase>(
        name = dbFile.absolutePath,
    )
}