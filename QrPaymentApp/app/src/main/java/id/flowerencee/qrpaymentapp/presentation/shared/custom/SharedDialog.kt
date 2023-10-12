package id.flowerencee.qrpaymentapp.presentation.shared.custom

import android.app.Activity
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import id.flowerencee.qrpaymentapp.presentation.shared.`object`.DialogData

fun Activity.showPopup(data: DialogData, listener: PopUpInterface? = null) {
    val dialog = MaterialAlertDialogBuilder(this)
    with(data) {
        title?.let {
            dialog.setTitle(it)
        }
        description?.let {
            dialog.setMessage(it)
        }
        positive?.let {
            dialog.setPositiveButton(it) { _, _ ->
                listener?.onPositive()
            }
        }
        negative?.let {
            dialog.setNegativeButton(it) { _, _ ->
                listener?.onNegative()
            }
        }
        neutral?.let {
            dialog.setNeutralButton(it) { _, _ ->
                listener?.onNeutral()
            }
        }
        icon?.let {
            dialog.setIcon(ContextCompat.getDrawable(this@showPopup, it))
        }
    }
    if (!isFinishing) dialog.show()
}

interface PopUpInterface {
    fun onPositive() {}
    fun onNegative() {}
    fun onNeutral() {}
}