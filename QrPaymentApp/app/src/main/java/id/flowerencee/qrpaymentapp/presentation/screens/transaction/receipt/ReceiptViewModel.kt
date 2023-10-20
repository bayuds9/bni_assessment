package id.flowerencee.qrpaymentapp.presentation.screens.transaction.receipt

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.flowerencee.qrpaymentapp.data.model.entity.Transaction
import id.flowerencee.qrpaymentapp.data.model.entity.UserAccount
import id.flowerencee.qrpaymentapp.domain.usecase.transaction.GetDetailTransactionUseCase
import id.flowerencee.qrpaymentapp.domain.usecase.useraccount.GetAccountUseCase
import id.flowerencee.qrpaymentapp.presentation.shared.extension.reformatCurrency
import id.flowerencee.qrpaymentapp.presentation.shared.`object`.TextLabel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ReceiptViewModel(
    private val getDetailTransactionUseCase: GetDetailTransactionUseCase,
    private val getAccountUseCase: GetAccountUseCase
) : ViewModel() {
    private var _transactionField = MutableLiveData<ArrayList<TextLabel>>()
    val transactionField: LiveData<ArrayList<TextLabel>> get() = _transactionField

    private var _transactionData = MutableLiveData<Transaction>()
    val transactionData: LiveData<Transaction> get() = _transactionData

    fun getTransactionData(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            getDetailTransactionUseCase.execute(id)?.let { trx ->
                withContext(Dispatchers.Main) {
                    _transactionData.value = trx
                }
                trx.transactionSource?.let { sourceId ->
                    getAccountUseCase.execute(sourceId)?.let { account ->
                        generateReceiptData(trx, account)
                    }
                }
            }
        }
    }

    private suspend fun generateReceiptData(trx: Transaction, account: UserAccount) {
        val list = ArrayList<TextLabel>()
        account.accountNumber?.let {
            list.add(TextLabel(list.size + 1, "Account Source", it))
        }
        account.accountOwner?.let {
            list.add(TextLabel(list.size + 1, "Account Owner", it))
        }
        when {
            trx.transactionName?.contains("QR", true) == true -> {
                trx.transactionDestination?.split(".")?.forEachIndexed { index, rawValue ->
                    val label = when (index) {
                        0 -> "Source of Bank"
                        1 -> "Transaction Number"
                        2 -> "Merchant Name"
                        3 -> "Bill Amount"
                        else -> ""
                    }
                    val value = if (index == 3 && rawValue.isDigitsOnly()) rawValue.toDouble()
                        .reformatCurrency("Rp") else rawValue
                    if (label.isNotEmpty()) list.add(TextLabel(index + 2, label, value))
                }
            }
        }
        trx.transactionAmount?.let {
            list.add(TextLabel(list.size + 1, "Paid", it.reformatCurrency("Rp")))
        }
        withContext(Dispatchers.Main) {
            _transactionField.value = list
        }
    }
}