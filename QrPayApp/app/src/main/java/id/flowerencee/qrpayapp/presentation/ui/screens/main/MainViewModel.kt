package id.flowerencee.qrpayapp.presentation.ui.screens.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.material.icons.filled.QrCode2
import androidx.lifecycle.ViewModel
import id.flowerencee.qrpayapp.presentation.support.navigation.Screen
import id.flowerencee.qrpayapp.presentation.objects.BottomNavItem

class MainViewModel : ViewModel() {

    fun generateBottomNavigation() = listOf(
        BottomNavItem("Dashboard", Icons.Default.Home, Screen.Dashboard),
        BottomNavItem("Bank Account", Icons.Default.CreditCard, Screen.MyAccount),
        BottomNavItem("Scan QR", Icons.Default.QrCode2, Screen.QrScanner),
        BottomNavItem("Cashflow Cart", Icons.Default.PieChart, Screen.Cart),
        BottomNavItem("About", Icons.Default.Info, Screen.About)
    )
}