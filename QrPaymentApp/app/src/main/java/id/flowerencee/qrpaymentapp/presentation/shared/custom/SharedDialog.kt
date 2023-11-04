package id.flowerencee.qrpaymentapp.presentation.shared.custom

import android.app.Activity
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import id.flowerencee.qrpaymentapp.R
import id.flowerencee.qrpaymentapp.databinding.DialogChallengeBinding
import id.flowerencee.qrpaymentapp.databinding.DialogCreateAccountBinding
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

fun Activity.showChallengePopup(listener: PopUpInterface) {
    val data = DialogData(
        getString(R.string.finish_challenge),
        positive = getString(R.string.submit),
        negative = getString(
            R.string.cancel
        )
    )
    val dialog = MaterialAlertDialogBuilder(this)
    val binding = DialogChallengeBinding.inflate(layoutInflater)
    val firstDigit = (1..10).random()
    val secondDigit = (1..10).random()
    val question = "$firstDigit + $secondDigit"
    binding.tvQuestion.text = question
    binding.etAnswer.apply {
        setType(InputView.TYPE.NUMBER)
    }
    dialog.setView(binding.root)
    with(data) {
        title?.let {
            dialog.setTitle(it)
        }
        description?.let {
            dialog.setMessage(it)
        }
        positive?.let {
            dialog.setPositiveButton(it) { _, _ ->
                val result = binding.etAnswer.getTextValue().toInt() == firstDigit + secondDigit
                listener.onResult(result)
            }
        }
        negative?.let {
            dialog.setNegativeButton(it) { _, _ ->
                listener.onNegative()
            }
        }
    }

    if (!isFinishing) dialog.show()
}

fun Activity.showTopUpBalancePopup(listener: PopUpInterface) {
    val data = DialogData(
        getString(R.string.top_up_balance),
        positive = getString(R.string.submit),
        negative = getString(
            R.string.cancel
        )
    )
    val dialog = MaterialAlertDialogBuilder(this)
    val binding = DialogChallengeBinding.inflate(layoutInflater)
    binding.etAnswer.apply {
        setType(InputView.TYPE.NUMBER)
    }
    dialog.setView(binding.root)
    with(data) {
        title?.let {
            dialog.setTitle(it)
        }
        description?.let {
            dialog.setMessage(it)
        }
        positive?.let {
            dialog.setPositiveButton(it) { _, _ ->
                val result = binding.etAnswer.getTextValue().toInt()
                listener.onResult(result)
            }
        }
        negative?.let {
            dialog.setNegativeButton(it) { _, _ ->
                listener.onNegative()
            }
        }
    }

    if (!isFinishing) dialog.show()
}

fun Activity.showCreateAccountPopup(listener: PopUpInterface) {
    val data = DialogData(
        getString(R.string.create_account),
        positive = getString(R.string.next),
        negative = getString(
            R.string.cancel
        )
    )
    val dialog = MaterialAlertDialogBuilder(this)
    val binding = DialogCreateAccountBinding.inflate(layoutInflater)
    binding.etName.apply {
        setType(InputView.TYPE.TEXT)
        enableClearText()
        setMaxLength(25)
        setHint(getString(R.string.account_owner_name))
    }
    binding.etAccountNumber.apply {
        setType(InputView.TYPE.NUMBER)
        enableClearText()
        setMaxLength(13)
        setHint(getString(R.string.account_number))
    }
    binding.etBalance.apply {
        setType(InputView.TYPE.CURRENCY)
        setHint(getString(R.string.initial_balance))
        setDone()
    }
    dialog.setView(binding.root)
    with(data) {
        title?.let {
            dialog.setTitle(it)
        }
        positive?.let {
            dialog.setPositiveButton(it) { _, _ ->
                listener.onCreateAccount(
                    binding.etName.getTextValue(),
                    binding.etAccountNumber.getTextValue(),
                    binding.etBalance.getTextValue().toDouble()
                )
            }
        }
        negative?.let {
            dialog.setNegativeButton(it) { _, _ ->
                listener.onNegative()
            }
        }
    }

    if (!isFinishing) dialog.show()

}

interface PopUpInterface {
    fun onPositive() {}
    fun onNegative() {}
    fun onNeutral() {}
    fun onResult(success: Boolean) {}
    fun onResult(balance: Int) {}
    fun onCreateAccount(name: String, number: String, balance: Double) {}
}