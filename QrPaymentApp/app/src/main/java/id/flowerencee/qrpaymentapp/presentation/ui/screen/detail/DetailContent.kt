package id.flowerencee.qrpaymentapp.presentation.ui.screen.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import id.flowerencee.qrpaymentapp.R
import id.flowerencee.qrpaymentapp.presentation.ui.component.appBarComponent
import id.flowerencee.qrpaymentapp.presentation.ui.component.listTextItem
import java.util.Date

@Composable
fun detailScreen(
    viewModel: DetailViewModel,
    transactionId: Int,
    onCloseReceipt: () -> Unit
) {
    viewModel.getTransactionData(transactionId)
    val transaction = viewModel.transactionData.collectAsState()
    val receiptField = viewModel.transactionField.collectAsState()
    Scaffold(
        topBar = {
            appBarComponent(
                title = stringResource(R.string.receipt)
            )
        },
        bottomBar = {
            Button(
                onClick = {
                    onCloseReceipt()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 8.dp)
            ) {
                Text(text = stringResource(id = R.string.close))
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Card(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    shape = MaterialTheme.shapes.large,
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.secondaryContainer)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            transaction.value.let {
                                val statusRemark =
                                    if (it.transactionStatus == true) stringResource(id = R.string.success) else stringResource(
                                        id = R.string.failed
                                    )
                                Text(
                                    text = stringResource(
                                        id = R.string.transaction_status,
                                        statusRemark
                                    ),
                                    style = MaterialTheme.typography.headlineMedium,
                                    color = if (it.transactionStatus == true) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.error,
                                    textAlign = TextAlign.Center
                                )
                                val time = it.transactionTime?.let { timeLong -> Date(timeLong) }
                                Text(
                                    text = time.toString(),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }

                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            items(receiptField.value) { data ->
                                listTextItem(data = data)
                            }
                        }
                    }
                }
            }
        }
    }
}