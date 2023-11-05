package id.flowerencee.qrpaymentapp.presentation.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import id.flowerencee.qrpaymentapp.R
import id.flowerencee.qrpaymentapp.presentation.shared.`object`.MenuData
import id.flowerencee.qrpaymentapp.presentation.theme.AppTheme

@Composable
fun menuContent(
    menuData: MenuData,
    onclick: (MenuData) -> Unit
) {
    val cardModifier = Modifier
        .padding(10.dp)
        .fillMaxWidth()
        .wrapContentHeight()
    val cardElevation = CardDefaults.cardElevation(
        defaultElevation = 4.dp
    )

    Card(
        modifier = cardModifier,
        shape = MaterialTheme.shapes.medium,
        elevation = cardElevation
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onclick(menuData) }
                .background(MaterialTheme.colorScheme.surfaceContainer)
        ) {
            Icon(
                tint = MaterialTheme.colorScheme.surfaceTint,
                painter = painterResource(id = menuData.icon),
                contentDescription = "Menu Illustration",
                modifier = Modifier
                    .size(80.dp)
                    .padding(8.dp)
            )
            Column(Modifier.padding(8.dp)) {
                Text(
                    text = menuData.name,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}


@Preview
@Composable
fun menuPreview() {
    AppTheme {
        val menuData = MenuData(0, "name", R.drawable.round_info, "")
        val onClick = { data: MenuData ->

        }
        menuContent(menuData = menuData, onclick = onClick)
    }
}