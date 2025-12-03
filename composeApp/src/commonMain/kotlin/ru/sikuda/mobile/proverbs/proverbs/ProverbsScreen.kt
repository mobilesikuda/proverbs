package ru.sikuda.mobile.proverbs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import proverbs.composeapp.generated.resources.Res
import proverbs.composeapp.generated.resources.app_name
import proverbs.composeapp.generated.resources.ic_arrow_back
import proverbs.composeapp.generated.resources.ic_search
import proverbs.composeapp.generated.resources.search
import ru.sikuda.mobile.proverbs.data.ProverbEntity

@Serializable
data class ListProverbRoute(val strFilter: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProverbsScreen(
    windowSize: WindowSizeClass?,
    strFind: String,
    proverbs: List<ProverbEntity>,
    onDetailClick: (Int, String) -> Unit
) {
    var fSearch by remember { mutableStateOf(strFind.isNotEmpty()) }
    //var fSearch by remember { mutableStateOf(false) }
    var query by remember { mutableStateOf(strFind) }
    val searchResults = remember { mutableStateListOf<ProverbEntity>() }

    if (proverbs.count() > 0 ) {
        searchResults.clear()
        if (query.isNotEmpty())
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
        else searchResults.addAll(proverbs)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    if(!fSearch) Text(stringResource(Res.string.app_name))
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                ),
                navigationIcon = {
                    Row(
                        //horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton( onClick = {
                            fSearch = !fSearch
                            if (!fSearch) query = ""
                        }) {
                            Icon(
                                painter = if(fSearch) painterResource(Res.drawable.ic_arrow_back)
                                          else painterResource(Res.drawable.ic_search),
                                contentDescription = "Search"
                            )
                        }
                        if (fSearch) {
                            Text(
                                stringResource(Res.string.search),
                                fontSize = 20.sp
                            )
                            OutlinedTextField(
                                value = query,
                                onValueChange = { newText ->
                                    query = newText
                                },
                                singleLine = true,
                                textStyle = TextStyle.Default.copy(fontSize = 20.sp),
                                modifier = Modifier.fillMaxWidth().padding(8.dp)
                            )
                        }
                    }
                },
                actions = {

                }
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                LazyColumn(
                    modifier = Modifier
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
                            if (
                                windowSize?.widthSizeClass != WindowWidthSizeClass.Compact &&
                                windowSize?.widthSizeClass != WindowWidthSizeClass.Medium
                                ){
                                    Text(
                                        text = item.description,
                                        modifier = Modifier.padding(2.dp)
                                    )
                            }
                        }
                        HorizontalDivider()
                    }
                }
            }
        }
    }
}




