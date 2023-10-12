@file:OptIn(ExperimentalMaterial3Api::class)

package id.flowerencee.qrpayapp.presentation.ui.screens.transaction

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import id.flowerencee.qrpayapp.R
import id.flowerencee.qrpayapp.presentation.support.navigation.Screen
import id.flowerencee.qrpayapp.presentation.ui.screens.main.dashboard.DashboardScreen
import id.flowerencee.qrpayapp.presentation.ui.screens.main.myaccount.MyAccount
import id.flowerencee.qrpayapp.presentation.ui.screens.main.qrscanner.QrScannerScreen
import id.flowerencee.qrpayapp.presentation.ui.screens.transaction.inquiry.InquiryScreen
import id.flowerencee.qrpayapp.presentation.ui.screens.transaction.receipt.ReceiptScreen

@Composable
fun TransactionView(qrMetaData: String) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                title = { Text(text = stringResource(id = R.string.app_name)) })
        },
        contentColor = MaterialTheme.colorScheme.surface,
        content = { padding ->
            val navController = rememberNavController()
            NavHost(navController = navController, Screen.InquiryTransaction.route, modifier = Modifier.padding(padding)) {
                addInquiryScreen(qrMetaData, navController, this)
                addReceiptScreen(navController, this)
            }
        }
    )
}

@OptIn(ExperimentalAnimationApi::class)
fun addInquiryScreen(
    qrMetaData: String,
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(route = Screen.InquiryTransaction.route) {
        InquiryScreen(
            qrMetaData,
            navController
        ) {
            navController.currentBackStackEntry?.savedStateHandle?.set(Screen.inquiryData, it)
            navController.navigate(Screen.Receipt.route)
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun addReceiptScreen(
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder
) {
    navGraphBuilder.composable(route = Screen.Receipt.route) {
        ReceiptScreen(navController)
    }
}

