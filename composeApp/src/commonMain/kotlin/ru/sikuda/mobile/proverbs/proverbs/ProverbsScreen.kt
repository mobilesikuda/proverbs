package ru.sikuda.mobile.proverbs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
    proverbs: List<ProverbEntity>,
    onDetailClick: (Int) -> Unit
){
    LazyColumn(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .safeContentPadding()
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        items( proverbs) { item ->
        //listProverbs.forEach { item ->
            Row(
                Modifier
                    .clickable( onClick =  { onDetailClick(item.uid) } )
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


