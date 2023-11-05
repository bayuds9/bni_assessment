package id.flowerencee.qrpaymentapp.presentation.ui.screen.promo

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import id.flowerencee.qrpaymentapp.R
import id.flowerencee.qrpaymentapp.data.model.response.promo.PromoItem
import id.flowerencee.qrpaymentapp.presentation.ui.component.appBarComponent
import id.flowerencee.qrpaymentapp.presentation.ui.component.promoItemContent

@Composable
fun promoScreen(
    navController: NavController,
    viewModel: PromosViewModel,
    onPromoClicked: (PromoItem) -> Unit
) {
    val listPromo = viewModel.listPromo.collectAsState()
    val statusResponse = viewModel.statusResponse.collectAsState()
    Scaffold(
        topBar = {
            appBarComponent(
                title = stringResource(id = R.string.promo),
                leftIcon = R.drawable.round_arrow_back
            ) {
                navController.navigateUp()
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                when (listPromo.value.isNotEmpty()) {
                    true -> {
                        items(listPromo.value) { data ->
                            promoItemContent(data = data) {
                                onPromoClicked(it)
                            }
                        }
                    }

                    else -> {
                        item {
                            Text(
                                text = stringResource(id = R.string.no_promo_to_show),
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                        statusResponse.value?.let {
                            item {
                                Text(
                                    text = it.toString(),
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                        item {
                            Button(onClick = {
                                viewModel.getAllPromo()
                            }, modifier = Modifier.fillMaxWidth()) {
                                Text(text = stringResource(id = R.string.retry))
                            }
                        }
                    }
                }
            }
        }
    }
}