package ru.sikuda.mobile.proverbs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.serialization.Serializable
import ru.sikuda.mobile.proverbs.data.ProverbEntity

@Serializable
data class ListProverbRoute(val name: String)

@Composable
fun ProverbsScreen(
    strFind: String,
    proverbs: List<ProverbEntity>,
    onDetailClick: (Int) -> Unit
){
    var text by remember { mutableStateOf(strFind) }
    var proverbsFilter by remember { mutableStateOf(proverbs) }

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        TextField(
            value = text,
            onValueChange = {
                newText -> text = newText
                if (newText.isEmpty())
                    proverbsFilter = proverbs
                else
                    proverbsFilter = proverbs.filter { it.title.contains(newText ) or
                        it.description.contains(newText ) }
            },
            //label = { Text("Найти") }
        )

        LazyColumn(
            modifier = Modifier
                //.background(MaterialTheme.colorScheme.background)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            items(proverbsFilter) { item ->
                Row(
                    Modifier
                        .clickable(onClick = { onDetailClick(item.uid) })
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


