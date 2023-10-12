package id.flowerencee.qrpayapp.presentation.ui.screens.main

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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import id.flowerencee.qrpayapp.R
import id.flowerencee.qrpayapp.presentation.support.navigation.Screen
import id.flowerencee.qrpayapp.presentation.ui.screens.main.dashboard.DashboardScreen
import id.flowerencee.qrpayapp.presentation.ui.screens.main.myaccount.MyAccount
import id.flowerencee.qrpayapp.presentation.ui.screens.main.qrscanner.QrScannerScreen
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: MainViewModel = koinViewModel()) {
    val navController = rememberAnimatedNavController()
    var currentTab = remember {
        mutableIntStateOf(0)
    }
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                title = { Text(text = stringResource(id = R.string.app_name)) })
        },
        contentColor = MaterialTheme.colorScheme.surface,
        bottomBar = {
            NavigationBar {
                viewModel.generateBottomNavigation().forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = currentTab.equals(index),
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            unselectedIconColor = MaterialTheme.colorScheme.onSurface,
                            selectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            indicatorColor = MaterialTheme.colorScheme.onSurface
                        ),
                        onClick = {
                            navController.navigate(item.route.route) {
                                popUpTo(item.route.route) {
                                    inclusive = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                            currentTab = mutableIntStateOf(index)
                        },
                        icon = {
                            Icon(item.icon, "Screen ${item.title}")
                        })
                }
            }
        },
        content = { padding ->
            AnimatedNavHost(
                navController = navController,
                startDestination = Screen.Dashboard.route,
                modifier = Modifier.padding(padding)
            ) {
                composable(route = Screen.Dashboard.route) {
                    DashboardScreen(navController = navController)
                }
                composable(route = Screen.MyAccount.route) {
                    MyAccount(navController)
                }
                composable(route = Screen.QrScanner.route) {
                    QrScannerScreen(navController = navController)
                }
            }
        }
    )
}