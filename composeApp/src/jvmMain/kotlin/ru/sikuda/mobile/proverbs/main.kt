package ru.sikuda.mobile.proverbs

import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import getDatabaseBuilder
import ru.sikuda.mobile.proverbs.data.ProverbDatabase
import ru.sikuda.mobile.proverbs.data.getRoomDatabase

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
fun main() = application {

    val db: ProverbDatabase by lazy {
        getRoomDatabase(getDatabaseBuilder())
    }

    Window(
        onCloseRequest = {
            db.close()
            exitApplication()
        },
        title = "Пословицы",
    ) {
        //val db  = getRoomDatabase(getDatabaseBuilder())
        val dao = db.proverbDao()
        val windowSize = calculateWindowSizeClass()
        App(dao, windowSize)
    }
}


