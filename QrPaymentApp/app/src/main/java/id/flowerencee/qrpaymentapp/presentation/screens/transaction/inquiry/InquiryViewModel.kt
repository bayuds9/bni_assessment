package id.flowerencee.qrpaymentapp.presentation.screens.transaction.inquiry

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import id.flowerencee.qrpaymentapp.data.model.entity.Transaction
import id.flowerencee.qrpaymentapp.data.model.entity.UserAccount
import id.flowerencee.qrpaymentapp.domain.usecase.transaction.AddTransactionUseCase
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
    private val getAllAccountUseCase: GetAllAccountUseCase
) : ViewModel() {

    private var _inquiryData = MutableLiveData<ArrayList<TextLabel>>()
    val inquiryData: LiveData<ArrayList<TextLabel>> get() = _inquiryData

    private var _transactionId = MutableLiveData<Int>()
    val transactionId: LiveData<Int> get() = _transactionId

    private var qrMetaData = ""
    private var accountData = UserAccount()
    private var amountValue = 0.0
    private var sofList = ArrayList<UserAccount>()

    fun setQrData(qrData: String) {
        qrMetaData = qrData
        generateTransactionData()
        qrMetaData.split(".").last().let {
            try {
                val origin = it.replace("[^0-9]".toRegex(), "")
                amountValue = origin.toDouble()
            } catch (e: NumberFormatException) {
                e.printStackTrace()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getBillerAmount() = amountValue.reformatCurrency()

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