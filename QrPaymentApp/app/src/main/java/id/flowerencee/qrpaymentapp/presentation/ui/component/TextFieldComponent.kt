package id.flowerencee.qrpaymentapp.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import id.flowerencee.qrpaymentapp.presentation.theme.AppTheme

@Composable
fun HintedTextField(
    initText: String,
    type: KeyboardType,
    onValueChange: (String) -> Unit,
    hint: String
) {
    var text by remember {
        mutableStateOf(initText)
    }
    val cardModifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .padding(horizontal = 8.dp)
    val cardElevation = CardDefaults.cardElevation(
        defaultElevation = 4.dp
    )
    Card(
        modifier = cardModifier,
        shape = MaterialTheme.shapes.medium,
        elevation = cardElevation
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(8.dp)
        ) {
            if (text.isEmpty()) {
                Text(
                    text = hint,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.padding(8.dp)
                )
            }
            BasicTextField(
                value = text,
                onValueChange = {
                    text = it
                    onValueChange(it)
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next,
                    keyboardType = type
                ),
                textStyle = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(8.dp)
            )
        }
    }
}

@Preview
@Composable
fun EditTextPreview() {
    AppTheme {
        var ownerName by remember {
            mutableStateOf("")
        }

        HintedTextField(
            initText = "",
            type = KeyboardType.Text,
            onValueChange = { newValue ->
                // Ensure that the input is numeric
                ownerName = newValue
            },
            hint = "Account Owner Name",
        )
    }
}