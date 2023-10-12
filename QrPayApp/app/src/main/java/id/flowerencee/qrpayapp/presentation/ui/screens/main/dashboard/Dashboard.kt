package id.flowerencee.qrpayapp.presentation.ui.screens.main.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import id.flowerencee.qrpayapp.data.entity.UserAccount
import id.flowerencee.qrpayapp.presentation.ui.shared.account.CardAccount
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(navController: NavController, viewModel: DashboardViewModel = koinViewModel()) {
    DashboardContent(navController)
}

@Composable
fun DashboardContent(
    navController: NavController,
    viewModel: DashboardViewModel = koinViewModel()
) {
    val accountList = viewModel.getAllAccount().observeAsState(initial = listOf())
    Column(modifier = Modifier.padding(top = 8.dp)) {
        Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Button(onClick = {
                val user = UserAccount(accountOwner = "Sukri", accountNumber = "12328381237812", balance =  1290000.0)
                viewModel.addAccount(user)
            }) {
                Text(text = "Add User")
            }
        }
        Row {
            accountList.value.let{ account ->
                LazyRow{
                    items(
                        account.size,
                        itemContent = {
                            account.forEach { acc ->
                                CardAccount(
                                    accountNumber = acc.accountNumber ?: "",
                                    ownerName = acc.accountOwner ?: "",
                                    balance = acc.balance ?: 0.0
                                ) {

                                }
                            }
                        }
                    )
                }
            }
        }
        Row {
            LazyColumn(
                modifier = Modifier.padding(start = 12.dp, end = 12.dp, top = 12.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp),
            ) {

            }
        }
    }
}
