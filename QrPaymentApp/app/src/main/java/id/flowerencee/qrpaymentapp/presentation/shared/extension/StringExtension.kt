package id.flowerencee.qrpaymentapp.presentation.shared.extension

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

fun Double.reformatCurrency(currency: String = ""): String {
    val myFormatter = DecimalFormat("###,###,###.##", DecimalFormatSymbols(Locale.GERMANY))
    return currency + myFormatter.format(this)
}