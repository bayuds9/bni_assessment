package id.flowerencee.qrpaymentapp.presentation.ui.screen.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import id.flowerencee.qrpaymentapp.presentation.shared.`object`.MenuData

@Composable
fun HomeScreen(
    listMenu: ArrayList<MenuData>,
    onMenuClicked: (MenuData) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
    ) {
        items(listMenu) { menu ->
            menuContent(menuData = menu, onclick = onMenuClicked)
        }
    }
}