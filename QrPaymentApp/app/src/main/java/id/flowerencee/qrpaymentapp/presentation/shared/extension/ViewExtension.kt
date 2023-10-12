package id.flowerencee.qrpaymentapp.presentation.shared.extension

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