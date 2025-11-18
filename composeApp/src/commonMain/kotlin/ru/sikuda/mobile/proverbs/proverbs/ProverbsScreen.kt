package ru.sikuda.mobile.proverbs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.stringResource
import proverbs.composeapp.generated.resources.Res
import proverbs.composeapp.generated.resources.app_name
import proverbs.composeapp.generated.resources.filterListLabel
import ru.sikuda.mobile.proverbs.data.ProverbEntity

@Serializable
data class ListProverbRoute(val strFilter: String)

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
                    Text( stringResource(Res.string.app_name))
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
            var strFilter by remember { mutableStateOf(strFind) }
            val proverbsFilter = listFilterProverbs(proverbs, strFilter).toMutableStateList()

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
                        stringResource(Res.string.filterListLabel),
                        fontSize = 20.sp,
                        modifier = Modifier.padding(8.dp),
                    )
                    OutlinedTextField(
                        value = strFilter,
                        onValueChange = { newText ->
                            strFilter = newText
                            fillListProverbs(proverbs, strFilter, proverbsFilter)
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
                                .clickable(onClick = { onDetailClick(item.uid, strFilter) })
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

fun listFilterProverbs(proverbs: List<ProverbEntity>, strFilter: String): List<ProverbEntity> {
    return proverbs.filter {
        it.title.contains(strFilter, ignoreCase = true) or
                it.description.contains(strFilter, ignoreCase = true)
    }
}

fun fillListProverbs(proverbs: List<ProverbEntity>, strFilter: String, proverbsFilter: SnapshotStateList<ProverbEntity>){

    proverbsFilter.clear()
    val list = listFilterProverbs(proverbs, strFilter)
    list.forEach {  item ->
        proverbsFilter.add( item )
    }
}


