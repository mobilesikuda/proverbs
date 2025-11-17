package ru.sikuda.mobile.proverbs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.serialization.Serializable
import ru.sikuda.mobile.proverbs.data.ProverbEntity

@Serializable
data class ListProverbRoute(val name: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProverbsScreen(
    strFind: String,
    proverbs: List<ProverbEntity>,
    onDetailClick: (Int, String) -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Пословицы",
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                ),
                actions = {

                }
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            var text by remember { mutableStateOf(strFind) }
            var proverbsFilter = proverbs.filter {
                it.title.contains(text) or
                        it.description.contains(text)
            }

            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Row(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.tertiary)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically, // Vertically center children
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        "Поиск по слову:",
                        fontSize = 20.sp,
                        modifier = Modifier.padding(8.dp),
                    )
                    OutlinedTextField(
                        value = text,
                        onValueChange = { newText ->
                            text = newText
                            proverbsFilter = proverbs.filter {
                                it.title.contains(text) or
                                        it.description.contains(text)
                            }
                        },
                        singleLine = true,
                        textStyle = TextStyle.Default.copy(fontSize = 20.sp),
                        modifier = Modifier.weight(1f).fillMaxWidth().padding(8.dp)
                        //label = { Text("Найти") }
                    )
                }

                LazyColumn(
                    modifier = Modifier
                        //.background(MaterialTheme.colorScheme.background)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    items(proverbsFilter) { item ->
                        Row(
                            Modifier
                                .clickable(onClick = { onDetailClick(item.uid, text) })
                        ) {
                            Text(
                                text = item.title,
                                modifier = Modifier
                                    .padding(8.dp),
                                fontSize = 24.sp
                            )
                            BoxWithConstraints {
                                if (maxWidth > 400.dp) {
                                    Text(
                                        text = item.description,
                                        modifier = Modifier.padding(2.dp)
                                    )
                                }
                            }
                        }
                        HorizontalDivider()
                    }
                }
            }
        }
    }
}


