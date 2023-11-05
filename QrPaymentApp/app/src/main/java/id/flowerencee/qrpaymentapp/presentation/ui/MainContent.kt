package id.flowerencee.qrpaymentapp.presentation.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import id.flowerencee.qrpaymentapp.presentation.shared.support.AppNavGraph
import id.flowerencee.qrpaymentapp.presentation.theme.AppTheme

@Composable
fun appMainContent(closeApp: () -> Unit) {
    AppTheme {
        val navController = rememberNavController()
        Scaffold { paddingValues ->
            AppNavGraph(
                modifier = Modifier.padding(paddingValues),
                closeApp = closeApp,
                navController = navController
            )
        }
    }
}