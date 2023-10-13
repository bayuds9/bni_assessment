package id.flowerencee.qrpaymentapp.presentation.shared.extension

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import id.flowerencee.qrpaymentapp.R

val FragmentManager.animatedTransaction: FragmentTransaction
    get() = this.beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right)

val FragmentManager.animatedEnterTransaction: FragmentTransaction
    get() = this.beginTransaction().setCustomAnimations(
        R.anim.enter_from_right,
        R.anim.no_animation,
        R.anim.no_animation,
        R.anim.exit_to_right
    )

fun Activity.hideSoftKeyboard() {
    val view = this.currentFocus
    if (view != null) {
        val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

fun View.toHide() {
    if (this.visibility != View.GONE) this.visibility = View.GONE
}

fun View.toSHow() {
    if (this.visibility != View.VISIBLE) this.visibility = View.VISIBLE
}