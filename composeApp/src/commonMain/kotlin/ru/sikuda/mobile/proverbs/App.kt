package ru.sikuda.mobile.proverbs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import ru.sikuda.mobile.proverbs.data.ProverbDao
import ru.sikuda.mobile.proverbs.data.ProverbEntity

@Composable
@Preview
fun App( proverbDao: ProverbDao) {
    MaterialTheme {
        val tests: List<ProverbEntity> = listOf()

        val proverbs by proverbDao.getAll().collectAsState(initial = emptyList())
        //val scope = rememberCoroutineScope()

        LaunchedEffect(true) {


            val userList = listOf(
                ProverbEntity(
                    "А воз и ныне там",
                    "(Цитата из басни И. А. Крылова. Смысл поговорки в том, что не смотря на все разговоры" +
                            " и обещания по какому-либо делу, ничего кроме болтовни не сделано.)")
            )
            userList.forEach {
                proverbDao.upsert(it)
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .safeContentPadding()
            ,
            contentPadding = PaddingValues(16.dp),
        ) {
            items( items = proverbs) { proverb ->
                Row( modifier = Modifier
                    .fillMaxWidth()
                ) {
                    Text( proverb.title.toString(), modifier = Modifier.padding(8.dp))
                    Text( proverb.description.toString(), modifier = Modifier.padding(8.dp))
                }
            }
        }

    }
}