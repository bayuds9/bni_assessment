package id.flowerencee.qrpayapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import id.flowerencee.qrpayapp.data.entity.Transaction
import id.flowerencee.qrpayapp.domain.usecase.transaction.AddTransactionUseCase
import id.flowerencee.qrpayapp.domain.usecase.useraccount.GetAllAccountUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PaymentViewModel(
    private val getAllAccountUseCase: GetAllAccountUseCase,
    private val addTransactionUseCase: AddTransactionUseCase
) : ViewModel() {
    fun getAccount() = liveData {
        getAllAccountUseCase.execute().collect() {
            emit(it)
        }
    }

    fun inquiryTransaction(transaction: Transaction) = viewModelScope.launch(Dispatchers.IO) {
        val rowId = addTransactionUseCase.execute(transaction)
        withContext(Dispatchers.Main) {

        }
    }
}