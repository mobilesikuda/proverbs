package ru.sikuda.mobile.proverbs

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import getDatabaseBuilder
import ru.sikuda.mobile.proverbs.data.getRoomDatabase
import java.awt.Dimension

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "proverbs",
    ) {
        window.minimumSize = Dimension(400, 400)
        val db  = getRoomDatabase(getDatabaseBuilder())
        val dao = db.proverbDao()
        App(dao)
    }
}