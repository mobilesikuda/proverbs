package ru.sikuda.mobile.proverbs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import ru.sikuda.mobile.proverbs.data.ProverbDatabase
import ru.sikuda.mobile.proverbs.data.getRoomDatabase

class MainActivity : ComponentActivity() {
    val db: ProverbDatabase by lazy {
        getRoomDatabase(getDatabaseBuilder(applicationContext))
    }

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val dao = db.proverbDao()
        setContent {
            val windowSize = calculateWindowSizeClass(this)
            App( dao, windowSize )
        }

    }

    override fun onDestroy() {
        db.close()
        super.onDestroy()
    }
}

