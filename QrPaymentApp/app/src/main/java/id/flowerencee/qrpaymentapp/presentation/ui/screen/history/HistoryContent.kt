package id.flowerencee.qrpaymentapp.presentation.ui.screen.history

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import id.flowerencee.qrpaymentapp.R
import id.flowerencee.qrpaymentapp.data.model.entity.UserAccount
import id.flowerencee.qrpaymentapp.presentation.ui.component.accountItem
import id.flowerencee.qrpaymentapp.presentation.ui.component.appBarComponent
import id.flowerencee.qrpaymentapp.presentation.ui.component.historyItem

@Composable
fun historyScreen(
    navController: NavController,
    viewModel: HistoriesViewModel,
    onClickTransaction: (Int) -> Unit
) {
    var screenTitle by remember {
        mutableStateOf("")
    }

    val accountsData = viewModel.accountList.collectAsState()
    val transactionsData = viewModel.transactionList.collectAsState()

    val onAccountClick: (UserAccount) -> Unit = { user ->
        user.id?.let {
            viewModel.getTransactions(it)
        }
        user.accountNumber?.let {
            screenTitle = it
        }
    }

    Scaffold(
        topBar = {
            screenTitle = stringResource(R.string.transaction_history)
            appBarComponent(
                title = screenTitle,
                leftIcon = R.drawable.round_arrow_back
            ) {
                navController.navigateUp()
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
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        items(accountsData.value) { data ->
                            accountItem(data = data) {
                                onAccountClick(it)
                            }
                        }
                    }
                }
                items(transactionsData.value) { data ->
                    historyItem(data = data) {
                        data.id?.let {
                            onClickTransaction(it)
                        }
                    }
                }
            }
        }
    }
}