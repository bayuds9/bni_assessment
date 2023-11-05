package id.flowerencee.qrpaymentapp.presentation.ui.screen.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.flowerencee.qrpaymentapp.data.model.entity.Transaction
import id.flowerencee.qrpaymentapp.data.model.entity.UserAccount
import id.flowerencee.qrpaymentapp.domain.usecase.transaction.AddTransactionUseCase
import id.flowerencee.qrpaymentapp.domain.usecase.useraccount.AddUserAccountUseCase
import id.flowerencee.qrpaymentapp.domain.usecase.useraccount.GetAllAccountUseCase
import id.flowerencee.qrpaymentapp.presentation.shared.extension.reformatCurrency
import id.flowerencee.qrpaymentapp.presentation.shared.`object`.TextLabel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TransactionViewModel(
    private val getAllAccountUseCase: GetAllAccountUseCase,
    private val addTransactionUseCase: AddTransactionUseCase,
    private val addUserAccountUseCase: AddUserAccountUseCase
) : ViewModel() {
    private var _inquiryData = MutableStateFlow<ArrayList<TextLabel>>(arrayListOf())
    val inquiryData: StateFlow<ArrayList<TextLabel>> get() = _inquiryData

    private var _transactionId = MutableStateFlow(-1)
    val transactionId: StateFlow<Int> get() = _transactionId

    private var _isHasAccount = MutableStateFlow(false)
    val isHasAccount: StateFlow<Boolean> get() = _isHasAccount

    private var qrMetaData = ""
    private var accountData = UserAccount()
    private var amountValue = 0.0

    private fun validateUserAccount() {
        viewModelScope.launch(Dispatchers.IO) {
            getAllAccountUseCase.execute().collect {
                withContext(Dispatchers.Main) {
                    _isHasAccount.value = it.isNotEmpty()
                }
            }
        }
    }

    fun setQrData(qrData: String) {
        qrMetaData = qrData
        qrMetaData.split(".").last().let {
            try {
                val origin = it.replace("[^0-9]".toRegex(), "")
                amountValue = origin.toDouble()
                validateUserAccount()
                initSof()
            } catch (e: NumberFormatException) {
                e.printStackTrace()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun initSof() {
        viewModelScope.launch(Dispatchers.IO) {
            getAllAccountUseCase.execute().collect() {
                withContext(Dispatchers.Main) {
                    it.firstOrNull { it.balance != null && it.balance!! > amountValue }.let { acc ->
                        if (acc?.balance != null) {
                            accountData = acc
                            generateTransactionData()
                        }
                    }
                }
            }
        }
    }

    private fun generateTransactionData() {
        val list = ArrayList<TextLabel>()
        if (qrMetaData.isNotEmpty()) {
            qrMetaData.split(".").forEachIndexed { index, value ->
                val label = when (index) {
                    0 -> "Source of Bank"
                    1 -> "Transaction Number"
                    2 -> "Merchant Name"
                    else -> ""
                }
                if (label.isNotEmpty()) list.add(TextLabel(index, label, value))
            }
        }
        if (accountData.id != null) {
            list.add(TextLabel(list.size + 1, "Account Number", accountData.accountNumber ?: ""))
            list.add(TextLabel(list.size + 1, "Account Owner", accountData.accountOwner ?: ""))
        }
        if (amountValue > 0.0) {
            list.add(TextLabel(list.size + 1, "Paid", amountValue.reformatCurrency("Rp")))
        }
        _inquiryData.value = list
    }

    fun createAccount(account: UserAccount) = viewModelScope.launch(Dispatchers.IO) {
        addUserAccountUseCase.execute(account)
        validateUserAccount()
    }

    fun executeTransaction() = viewModelScope.launch(Dispatchers.IO) {
        val transaction = Transaction(
            transactionName = "Transaction Qr",
            transactionAmount = amountValue,
            transactionSource = accountData.id,
            transactionDestination = qrMetaData
        )
        val rowId = addTransactionUseCase.execute(transaction)
        withContext(Dispatchers.Main) {
            _transactionId.value = rowId.toInt()
        }
    }
}