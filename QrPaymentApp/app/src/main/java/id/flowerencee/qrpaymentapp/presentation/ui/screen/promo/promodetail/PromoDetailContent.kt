package id.flowerencee.qrpaymentapp.presentation.ui.screen.promo.promodetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import id.flowerencee.qrpaymentapp.R
import id.flowerencee.qrpaymentapp.data.model.response.promo.PromoItem
import id.flowerencee.qrpaymentapp.presentation.ui.component.appBarComponent
import id.flowerencee.qrpaymentapp.presentation.ui.component.listTextItem
import id.flowerencee.qrpaymentapp.presentation.ui.component.promoItemContent

@Composable
fun promoDetailScreen(
    promoData: PromoItem,
    viewModel: PromoDetailViewModel,
    onBackPress: () -> Unit
) {
    var screenTitle by remember {
        mutableStateOf("")
    }
    viewModel.promoTitle.collectAsState().let {
        screenTitle = it.value
    }
    val promoField = viewModel.promoDetail.collectAsState()
    Scaffold(
        topBar = {
            appBarComponent(
                title = screenTitle,
                leftIcon = R.drawable.round_arrow_back
            ) {
                onBackPress()
            }
        }
    )
    { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                item {
                    promoItemContent(data = promoData) {
                    }
                }
                items(promoField.value) { data ->
                    listTextItem(data = data)
                }
            }
        }
    }
}