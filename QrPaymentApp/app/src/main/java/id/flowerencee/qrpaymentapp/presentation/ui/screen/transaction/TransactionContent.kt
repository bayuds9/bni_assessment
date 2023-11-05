package id.flowerencee.qrpaymentapp.presentation.ui.screen.transaction

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import androidx.navigation.NavController
import id.flowerencee.qrpaymentapp.R
import id.flowerencee.qrpaymentapp.data.model.entity.UserAccount
import id.flowerencee.qrpaymentapp.presentation.shared.support.DeLog
import id.flowerencee.qrpaymentapp.presentation.ui.component.appBarComponent
import id.flowerencee.qrpaymentapp.presentation.ui.component.createAccountDialog
import id.flowerencee.qrpaymentapp.presentation.ui.component.listTextItem

@Composable
fun transactionScreen(
    navController: NavController,
    viewModel: TransactionViewModel,
    qrData: String,
    onSuccessTransaction: (Int) -> Unit
) {
    viewModel.setQrData(qrData)

    val isUserHasAccount = viewModel.isHasAccount.collectAsState()
    val inquiryData = viewModel.inquiryData.collectAsState()
    var executeClicked by remember {
        mutableStateOf(false)
    }
    viewModel.transactionId.collectAsState().let {
        if (executeClicked && it.value > -1) onSuccessTransaction(it.value)
    }

    var createAccountAction by remember {
        mutableStateOf(false)
    }

    if (createAccountAction) {
        createAccountDialog { ownerName: String, accountNumber: String, accounBalance: String ->
            if (ownerName.isNotEmpty() && accountNumber.isNotEmpty() && accounBalance.isNotEmpty() && accounBalance.isDigitsOnly()) {
                try {
                    val account = UserAccount(
                        accountOwner = ownerName,
                        accountNumber = accountNumber,
                        balance = accounBalance.toDouble()
                    )
                    viewModel.createAccount(account)
                } catch (e: NumberFormatException) {
                    e.printStackTrace()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            createAccountAction = false
        }
    }

    Scaffold(
        topBar = {
            appBarComponent(
                title = stringResource(R.string.inquiry),
                leftIcon = R.drawable.round_arrow_back
            ) {
                navController.navigateUp()
            }
        },
        bottomBar = {
            if (isUserHasAccount.value) {
                Button(
                    onClick = {
                        viewModel.executeTransaction()
                        executeClicked = true
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 8.dp)
                ) {
                    Text(text = stringResource(id = R.string.inquiry))
                }
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
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (!isUserHasAccount.value) {
                    Button(onClick = {
                        DeLog.d("CLICKED", "SHOW POPUP CREATE ACCOUNT")
                        createAccountAction = true
                    }) {
                        Text(text = stringResource(id = R.string.create_account))
                    }
                }
                Card(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    shape = MaterialTheme.shapes.large,
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .background(MaterialTheme.colorScheme.secondaryContainer)
                    ) {
                        items(inquiryData.value) { data ->
                            listTextItem(data = data)
                        }
                    }
                }
            }
        }
    }
}