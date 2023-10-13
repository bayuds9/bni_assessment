package id.flowerencee.qrpaymentapp.presentation.screens.transaction.inquiry

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import id.flowerencee.qrpaymentapp.data.entity.Transaction
import id.flowerencee.qrpaymentapp.data.entity.UserAccount
import id.flowerencee.qrpaymentapp.domain.usecase.transaction.AddTransactionUseCase
import id.flowerencee.qrpaymentapp.domain.usecase.transaction.GetDetailTransactionUseCase
import id.flowerencee.qrpaymentapp.domain.usecase.useraccount.GetAccountUseCase
import id.flowerencee.qrpaymentapp.domain.usecase.useraccount.GetAllAccountUseCase
import id.flowerencee.qrpaymentapp.presentation.shared.extension.reformatCurrency
import id.flowerencee.qrpaymentapp.presentation.shared.`object`.TextLabel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class InquiryViewModel(
    private val addTransactionUseCase: AddTransactionUseCase,
    private val getAccountUseCase: GetAccountUseCase,
    private val getAllAccountUseCase: GetAllAccountUseCase,
    private val getDetailTransactionUseCase: GetDetailTransactionUseCase
) : ViewModel() {

    private var _status = MutableLiveData<Boolean>()
    val status: LiveData<Boolean> get() = _status

    private var _inquiryData = MutableLiveData<ArrayList<TextLabel>>()
    val inquiryData: LiveData<ArrayList<TextLabel>> get() = _inquiryData

    private var qrMetaData = ""
    private var accountData = UserAccount()
    private var amountValue = 0.0
    private var sofList = ArrayList<UserAccount>()

    fun setQrData(qrData: String) {
        qrMetaData = qrData
        generateTransactionData()
    }

    fun getSof() = liveData {
        getAllAccountUseCase.execute().collect() {
            withContext(Dispatchers.Main) {
                sofList = ArrayList(it)
            }
            emit(it)
        }
    }

    fun inquiryTransaction(amount: Double, sofText: String) {
        amountValue = amount
        val id = sofText.split("-").first().let { num ->
            sofList.firstOrNull { it.accountNumber == num }?.id ?: 0
        }
        setSofData(id)
    }

    private fun generateTransactionData() {
        val list = ArrayList<TextLabel>()
        if (qrMetaData.isNotEmpty()) {
            qrMetaData.split(".").forEachIndexed { index, rawValue ->
                val label = when (index) {
                    0 -> "Source of Bank"
                    1 -> "Transaction Number"
                    2 -> "Merchant Name"
                    3 -> "Bill Amount"
                    else -> ""
                }
                val value = if (index == 3 && rawValue.isDigitsOnly()) rawValue.toDouble().reformatCurrency("Rp") else rawValue
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

    private fun setSofData(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        getAccountUseCase.execute(id)?.let {
            withContext(Dispatchers.Main) {
                accountData = it
                generateTransactionData()
            }
        }
    }

    fun executeTransaction() = viewModelScope.launch(Dispatchers.IO) {
        val transaction = Transaction(
            transactionAmount = amountValue,
            transactionSource = accountData.id,
            transactionDestination = qrMetaData
        )
        val rowId = addTransactionUseCase.execute(transaction)
        withContext(Dispatchers.Main) {
            _status.value = rowId > 0
            generateReceipt(rowId.toInt())
        }
    }

    private fun generateReceipt(rowId: Int) = viewModelScope.launch(Dispatchers.IO) {
        getDetailTransactionUseCase.execute(rowId)?.let {
            withContext(Dispatchers.Main){
                qrMetaData = it.transactionDestination ?: ""
                if (it.transactionAmount != null) amountValue = it.transactionAmount!!
                if (it.transactionDestination != null && it.transactionDestination?.isDigitsOnly() == true) setSofData(it.transactionDestination!!.toInt())

            }
        }
    }

}