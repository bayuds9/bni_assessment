package id.flowerencee.qrpaymentapp.presentation.shared

import android.content.Context
import android.content.Intent
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Parcelable
import android.util.TypedValue
import androidx.appcompat.app.AppCompatDelegate
import id.flowerencee.qrpaymentapp.R

inline fun <reified T : Parcelable> Intent.parcelable(key: String): T? = when {
    SDK_INT >= 33 -> getParcelableExtra(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelableExtra(key) as? T
}

inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? = when {
    SDK_INT >= 33 -> getParcelable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelable(key) as? T
}

fun Context.getColors(index: Int): Int {
    val customColorsLight = listOf(
        R.color.customColor1Light,
        R.color.customColor2Light,
        R.color.customColor3Light,
        R.color.customColor4Light
    )

    val customColorsDark = listOf(
        R.color.customColor1Dark,
        R.color.customColor2Dark,
        R.color.customColor3Dark,
        R.color.customColor4Dark
    )
    return getThemeBasedColor(this, customColorsLight, customColorsDark, index)
}

fun getThemeBasedColor(context: Context, lightColors: List<Int>, darkColors: List<Int>, index: Int): Int {
    val nightMode = AppCompatDelegate.getDefaultNightMode()
    val lightIndex = index % lightColors.size
    val darkIndex = index % darkColors.size

    return when (nightMode) {
        AppCompatDelegate.MODE_NIGHT_NO -> {
            // Light mode
            context.resources.getColor(lightColors[lightIndex])
        }
        AppCompatDelegate.MODE_NIGHT_YES -> {
            // Dark mode
            context.resources.getColor(darkColors[darkIndex])
        }
        else -> {
            // Default to light mode
            context.resources.getColor(lightColors[lightIndex])
        }
    }
}


fun Context.getColorAttribute(attrId: Int): Int {
    val typedValue = TypedValue()
    val theme = this.theme
    theme.resolveAttribute(attrId, typedValue, true)
    return typedValue.data
}