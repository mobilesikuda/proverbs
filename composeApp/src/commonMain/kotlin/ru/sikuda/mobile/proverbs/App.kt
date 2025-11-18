package ru.sikuda.mobile.proverbs

//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import org.jetbrains.compose.ui.tooling.preview.Preview
import ru.sikuda.mobile.proverbdetail.DetailProverbRoute
import ru.sikuda.mobile.proverbdetail.ProverbDetailScreen
import ru.sikuda.mobile.proverbs.data.ProverbDao
import ru.sikuda.mobile.proverbs.data.ProverbEntity
import ru.sikuda.mobile.proverbs.utils.loadAllData

@Composable
@Preview
fun App( proverbDao: ProverbDao) {

    val proverbs by proverbDao.getAll().collectAsState(initial = emptyList())
    //var strFind by remember { mutableStateOf("") }
    val navController = rememberNavController()

    LaunchedEffect(true) {
        loadAllData(proverbDao)
    }

    NavHost(navController, startDestination = ListProverbRoute("крылов")) {
        composable<DetailProverbRoute> { backStackEntry ->
            val route: DetailProverbRoute = backStackEntry.toRoute()
            val item = proverbs.firstOrNull { it.uid.toString() == route.id } ?: ProverbEntity("", "", 1)
            ProverbDetailScreen( item, { navController.popBackStack() })
        }
        composable<ListProverbRoute> { backStackEntry ->
            val route: ListProverbRoute = backStackEntry.toRoute()
            ProverbsScreen(
                route.strFilter,
                proverbs,
                //{ text -> navController.navigate(ListProverbRoute(text )) },
                { id, text ->
                    navController.navigate(ListProverbRoute( text ))
                    navController.navigate(DetailProverbRoute(id.toString())) })
        }
        //depricated
//        composable("home")
//        {
//            TopBarNavigation(strFind, proverbs, navController) { navController.popBackStack() }
//        }
    }
}

