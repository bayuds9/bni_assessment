package id.flowerencee.qrpayapp.presentation.support.navigation

sealed class Screen(val route: String) {
    object Dashboard : Screen("DASHBOARD")
    object QrScanner : Screen("QR_SCANNER")
    object MyAccount : Screen("MY_ACCOUNT")
    object Cart : Screen("CART")
    object About : Screen("ABOUT")
    object InquiryTransaction : Screen("INQUIRY_TRANSACTION")
    object Receipt : Screen("RECEIPT")

    companion object {
        const val inquiryData = "INQUIRY_DATA"
    }
}
