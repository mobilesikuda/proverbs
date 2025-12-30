import androidx.room.Room
import androidx.room.RoomDatabase
import ru.sikuda.mobile.proverbs.data.ProverbDatabase
import java.io.File

fun getDatabaseBuilder(): RoomDatabase.Builder<ProverbDatabase> {
    val dbFile = File(System.getProperty("user.home"), "proverbDatabase.db") //java.io.tmpdir ..user.dir
    return Room.databaseBuilder<ProverbDatabase>(
        name = dbFile.absolutePath,
    )
}