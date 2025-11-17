package ru.sikuda.mobile.proverbs

import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import proverbs.composeapp.generated.resources.Res
import proverbs.composeapp.generated.resources.arrow_back_24dp
import ru.sikuda.mobile.proverbdetail.DetailProverbRoute
import ru.sikuda.mobile.proverbdetail.ProverbDetailScreen
import ru.sikuda.mobile.proverbs.data.ProverbDao
import ru.sikuda.mobile.proverbs.data.ProverbEntity
import ru.sikuda.mobile.proverbs.utils.loadAllData

@Composable
@Preview
fun App( proverbDao: ProverbDao) {


    val proverbs by proverbDao.getAll().collectAsState(initial = emptyList())
    var strFind by remember { mutableStateOf("") }
    val navController = rememberNavController()

    LaunchedEffect(true) {
        loadAllData(proverbDao)
    }

    NavHost(navController, startDestination = ListProverbRoute("")) {
        composable<DetailProverbRoute> { backStackEntry ->
            val route: DetailProverbRoute = backStackEntry.toRoute()
            TopBarNavigation(strFind, proverbs, navController, route) { navController.popBackStack() }
        }
        composable<ListProverbRoute> { backStackEntry ->
            val route: ListProverbRoute = backStackEntry.toRoute()
            TopBarNavigation( strFind, proverbs, navController, route) { navController.popBackStack() }
        }
        //depricated
        composable("home")
        {
            TopBarNavigation(strFind, proverbs, navController) { navController.popBackStack() }
        }
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarNavigation(
    strFind: String,
    proverbs: List<ProverbEntity>,
    navController: NavHostController,
    route: Any? = null,
    navigateBack: () -> Unit,
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
                navigationIcon = {
                    if (route is DetailProverbRoute) {
                        IconButton(onClick = navigateBack) {
                            Icon(
                                painter = painterResource(Res.drawable.arrow_back_24dp),
                                contentDescription = "Back"
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
            when(route) {
                is DetailProverbRoute -> {
                    val item = proverbs.firstOrNull{ it.uid.toString() == route.id } ?: ProverbEntity("","",666)
                    ProverbDetailScreen(item)
                }
                else -> ProverbsScreen(
                    strFind,
                    proverbs,
                    { id-> navController.navigate( DetailProverbRoute( id.toString() )) }
                )
            }
        }

    }
}