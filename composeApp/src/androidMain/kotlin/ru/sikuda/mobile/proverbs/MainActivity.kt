package ru.sikuda.mobile.proverbs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import ru.sikuda.mobile.proverbs.data.ProverbDatabase
import ru.sikuda.mobile.proverbs.data.getRoomDatabase

class MainActivity : ComponentActivity() {
    val db: ProverbDatabase by lazy {
        getRoomDatabase(getDatabaseBuilder(applicationContext))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val dao = db.proverbDao()
        setContent {
            App( dao )
        }

    }

    override fun onDestroy() {
        db.close()
        super.onDestroy()
    }
}

