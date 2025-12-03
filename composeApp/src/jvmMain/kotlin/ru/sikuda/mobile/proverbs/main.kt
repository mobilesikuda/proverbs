package ru.sikuda.mobile.proverbs

import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import getDatabaseBuilder
import ru.sikuda.mobile.proverbs.data.getRoomDatabase

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "proverbs",
    ) {
        //window.setSize(600, 600)
        //window.minimumSize = Dimension(400, 400)
        val db  = getRoomDatabase(getDatabaseBuilder())
        val dao = db.proverbDao()
        val windowSize = calculateWindowSizeClass()
        App(dao, windowSize)
    }
}