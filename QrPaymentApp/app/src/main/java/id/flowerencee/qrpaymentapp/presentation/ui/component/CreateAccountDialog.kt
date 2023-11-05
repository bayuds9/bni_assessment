package id.flowerencee.qrpaymentapp.presentation.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import id.flowerencee.qrpaymentapp.R

@Composable
fun createAccountDialog(
    onDismiss: (String, String, String) -> Unit
) {
    var ownerName by remember {
        mutableStateOf("")
    }
    var accountNumber by remember {
        mutableStateOf("")
    }
    var accountBalance by remember {
        mutableStateOf("")
    }
    Dialog(
        onDismissRequest = {
            onDismiss(ownerName, accountNumber, accountBalance)
        }
    ) {
        // Dialog content
        Card(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(8.dp)
            ) {
                Text(
                    text = stringResource(R.string.create_account),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Edit Text 1
                HintedTextField(
                    initText = "",
                    type = KeyboardType.Text,
                    onValueChange = { newValue ->
                        // Ensure that the input is numeric
                        ownerName = newValue
                    },
                    hint = "Account Owner Name",
                )

                Spacer(modifier = Modifier.height(8.dp))
                HintedTextField(
                    initText = "",
                    type = KeyboardType.Number,
                    onValueChange = { newValue ->
                        // Ensure that the input is numeric
                        accountNumber = newValue
                    },
                    hint = "Account Number",
                )

                Spacer(modifier = Modifier.height(8.dp))
                HintedTextField(
                    initText = "0",
                    type = KeyboardType.Number,
                    onValueChange = { newValue ->
                        // Ensure that the input is numeric
                        accountBalance = newValue
                    },
                    hint = "Init Balance",
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Button to dismiss the dialog
                Button(
                    onClick = {
                        onDismiss(ownerName, accountNumber, accountBalance)
                    },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                ) {
                    Text(text = "OK")
                }
            }
        }
    }
}