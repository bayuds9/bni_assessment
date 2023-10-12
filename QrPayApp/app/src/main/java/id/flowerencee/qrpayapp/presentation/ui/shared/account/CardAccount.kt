package id.flowerencee.qrpayapp.presentation.ui.shared.account

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import id.flowerencee.qrpayapp.presentation.ui.theme.QrPayAppTheme

@Composable
fun CardAccount(accountNumber: String, ownerName: String, balance: Double, onClickAccount: () -> Unit) {
    Card(
        shape = RoundedCornerShape(14.dp),
        modifier = Modifier.padding(10.dp, 5.dp, 10.dp, 10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
    ) {
        Column(modifier = Modifier
            .clickable(onClick = onClickAccount)
            .padding(16.dp)
            .fillMaxWidth(2f)) {
            Text(
                text = ownerName,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.secondary
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = balance.toString(),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier
                    .align(Alignment.End)
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = accountNumber,
                style = MaterialTheme.typography.titleLarge,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun CardAccountPreview() {
    QrPayAppTheme {
        CardAccount(accountNumber = "123456", ownerName = "nanana", balance = 2000000.0) {

        }
    }
}