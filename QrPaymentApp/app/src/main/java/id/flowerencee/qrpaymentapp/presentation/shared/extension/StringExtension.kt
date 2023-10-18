package id.flowerencee.qrpaymentapp.presentation.shared.extension

import org.json.JSONObject
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties

fun Double.reformatCurrency(currency: String = ""): String {
    val myFormatter = DecimalFormat("###,###,###.##", DecimalFormatSymbols(Locale.GERMANY))
    return currency + myFormatter.format(this)
}

/*Reformat numeric value*/
fun String.toNumberFormat(): String {
    val plain = this.replace(".", "")
    val formBuilder = StringBuilder()
    for ((index, value) in plain.reversed().withIndex()) {
        if (index % 3 == 0 && index > 0) formBuilder.append(".$value")
        else formBuilder.append(value)
    }
    return formBuilder.toString().reversed()
}

/*Convert Kotlin Data Class Into Map*/
fun <T : Any> toMap(obj: T): Map<String, Any?> {
    return (obj::class as KClass<T>).memberProperties.associate { prop ->
        prop.name to prop.get(obj)?.let { value ->
            if (value::class.isData) {
                toMap(value)
            } else {
                value
            }
        }
    }
}

/*Reformat String value from map into better reading experience*/
fun String.formatFirstLetter(): String {
    val raw = if (this.contains("_")) this.replace("_", " ") else this
    val capital =
        raw.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
    val result = java.lang.StringBuilder()
    capital.forEachIndexed { index, c ->
        when {
            c.isUpperCase() && index > 0 -> result.append(" $c")
            else -> result.append(c)
        }
    }
    return result.toString()
}

fun isJsonObject(str: String): Boolean {
    return try {
        JSONObject(str)
        true
    } catch (e: Exception) {
        false
    }
}