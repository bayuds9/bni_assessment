package id.flowerencee.qrpaymentapp.presentation.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import id.flowerencee.qrpaymentapp.presentation.shared.`object`.TextLabel

@Composable
fun listTextItem(
    data: TextLabel
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp)
    ) {
        data.label.let {
            if (it.isNotEmpty()) {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Start
                )
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
        data.value.let {
            if (it.isNotEmpty()) {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    textAlign = TextAlign.End
                )
            }
        }
    }
}