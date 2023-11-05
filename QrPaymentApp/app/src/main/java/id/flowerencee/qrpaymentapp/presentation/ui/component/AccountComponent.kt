package id.flowerencee.qrpaymentapp.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import id.flowerencee.qrpaymentapp.data.model.entity.UserAccount
import id.flowerencee.qrpaymentapp.presentation.shared.extension.reformatCurrency
import id.flowerencee.qrpaymentapp.presentation.theme.AppTheme


@Composable
fun accountItem(
    data: UserAccount,
    onClicked: (UserAccount) -> Unit
) {
    val cardModifier = Modifier
        .padding(8.dp)
        .width(250.dp)
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
                .background(MaterialTheme.colorScheme.tertiaryContainer)
        ) {
            Column(Modifier.padding(16.dp)) {
                data.accountNumber?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onTertiaryContainer,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                data.accountOwner?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                data.balance?.let {
                    Text(
                        text = it.reformatCurrency("Rp"),
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.End,
                        color = MaterialTheme.colorScheme.onTertiaryContainer,
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
fun AccountItemPreview() {
    AppTheme {
        val data = UserAccount(null, "sukri", "12i3782193", 12837283.0)
        accountItem(data = data) {

        }
    }
}