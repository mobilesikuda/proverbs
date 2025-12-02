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
data class ListProverbRoute(val strFilter: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProverbsScreen(
    strFind: String,
    proverbs: List<ProverbEntity>,
    onDetailClick: (Int, String) -> Unit
) {

    var query by remember { mutableStateOf("") }
    val searchResults = remember { mutableStateListOf<ProverbEntity>() }

    if (proverbs.count() > 0 ) {
        LaunchedEffect(query) {
            searchResults.clear()
            searchResults.addAll(
                proverbs.filter {
                    it.title.contains(
                        other = query,
                        ignoreCase = true,
                    ) ||
                            it.description.contains(
                                other = query,
                                ignoreCase = true,
                            )
                },
            )
        }
    }

    Scaffold(
        topBar = {
            OutlinedTextField(
                value = query,
                onValueChange = { newText ->
                    query = newText
                    //fillListProverbs(proverbs, strFilter, proverbsFilter)
                },
                singleLine = true,
                textStyle = TextStyle.Default.copy(fontSize = 20.sp),
                modifier = Modifier.fillMaxWidth().padding(8.dp)
            )
//            CenterAlignedTopAppBar(
//                title = {
//                    Text( stringResource(Res.string.app_name))
//                },
//                colors = TopAppBarDefaults.topAppBarColors(
//                    containerColor = MaterialTheme.colorScheme.secondaryContainer
//                ),
//                actions = {
//
//                }
//            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            //var strFilter by remember { mutableStateOf(strFind) }
            //val proverbsFilter = listFilterProverbs(proverbs, strFilter).toMutableStateList()

            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
//                Row(
//                    modifier = Modifier
//                        .background(MaterialTheme.colorScheme.tertiary)
//                        .fillMaxWidth(),
//                    verticalAlignment = Alignment.CenterVertically, // Vertically center children
//                    horizontalArrangement = Arrangement.spacedBy(8.dp)
//                ) {
//                    Text(
//                        stringResource(Res.string.filterListLabel),
//                        fontSize = 20.sp,
//                        modifier = Modifier.padding(8.dp),
//                    )
//
//                }

                LazyColumn(
                    modifier = Modifier
                        //.background(MaterialTheme.colorScheme.background)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    items(searchResults) { item ->
                        Row(
                            Modifier
                                .clickable(onClick = { onDetailClick(item.uid, query) })
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

//fun listFilterProverbs(proverbs: List<ProverbEntity>, strFilter: String): List<ProverbEntity> {
//    return proverbs.filter {
//        it.title.contains(strFilter, ignoreCase = true) or
//                it.description.contains(strFilter, ignoreCase = true)
//    }
//}
//
//fun fillListProverbs(proverbs: List<ProverbEntity>, strFilter: String, proverbsFilter: SnapshotStateList<ProverbEntity>){
//    proverbsFilter.clear()
//    val list = listFilterProverbs(proverbs, strFilter)
//    list.forEach {  item ->
//        proverbsFilter.add( item )
//    }
//}


