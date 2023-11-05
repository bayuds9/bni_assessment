package id.flowerencee.qrpaymentapp.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import id.flowerencee.qrpaymentapp.data.model.entity.Transaction
import id.flowerencee.qrpaymentapp.presentation.shared.extension.reformatCurrency
import id.flowerencee.qrpaymentapp.presentation.theme.AppTheme
import java.util.Date

@Composable
fun historyItem(
    data: Transaction,
    onClicked: (Transaction) -> Unit
) {
    val cardModifier = Modifier
        .padding(8.dp)
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
                .clickable { onClicked(data) }
                .background(MaterialTheme.colorScheme.primaryContainer)
        ) {
            Column(Modifier.padding(16.dp)) {
                data.transactionAmount?.let {
                    Text(
                        text = it.reformatCurrency("Rp"),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                data.transactionName?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                data.transactionDestination?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                data.transactionTime?.let {
                    Text(
                        text = Date(it).toString(),
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.End,
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.End)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun HistoryItemPreview() {
    AppTheme {
        val data =
            Transaction(0, "128371823", "qr payment", 50000.0, 0, null, "bank bni", 1273618, true)
        historyItem(data = data) {

        }
    }
}