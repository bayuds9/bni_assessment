package id.flowerencee.qrpaymentapp.presentation.shared.support

sealed class RouteTo(val screen: String) {
    object MENU : RouteTo("menu_screen")
    object SCANNER : RouteTo("scanner_screen")
    object PROMO : RouteTo("promo_screen")
    object PROMO_DETAIL : RouteTo("promo_detail_screen")
    object HISTORY : RouteTo("history_screen")
    object TRANSACTION : RouteTo("transaction_screen")
    object DETAIL : RouteTo("detail_screen")
    object ABOUT : RouteTo("about_screen")
    object CHART : RouteTo("chart_screen")
}
