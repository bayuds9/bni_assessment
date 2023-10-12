package id.flowerencee.qrpayapp.support.classes

import androidx.navigation.NavHostController
import id.flowerencee.qrpayapp.presentation.support.navigation.Screen

class NavActions(navController: NavHostController, startDestination: String) {
    val popToStart = {
        navController.navigate(route = startDestination) {
            popUpTo(startDestination) {
                saveState = true
            }
            launchSingleTop = true
        }
    }

    val toBankAccount: () -> Unit = {
        navController.navigate(route = Screen.MyAccount.route) {
            popToStart()
        }
    }

    val toScanQR: () -> Unit = {
        navController.navigate(route = Screen.QrScanner.route) {
            popToStart()
        }
    }

    val toCashflowCart: () -> Unit = {
        navController.navigate(route = Screen.Cart.route) {
            popToStart()
        }
    }

    val toAbout: () -> Unit = {
        navController.navigate(route = Screen.About.route) {
            popToStart()
        }
    }
}