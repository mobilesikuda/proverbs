package ru.sikuda.mobile.proverbdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
data class DetailProverbRoute(val id: String)

@Composable
fun ProverbDetailScreen(
    item: ProverbEntity,
){
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {

        Text(
            item.title,
            fontSize = 30.sp,
            modifier = Modifier.padding(16.dp))

        HorizontalDivider(
            modifier = Modifier.padding(16.dp)
        )

        Text(item.description, modifier = Modifier.padding(16.dp))
    }
}