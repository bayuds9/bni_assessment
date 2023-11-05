package id.flowerencee.qrpaymentapp.presentation.ui.screen.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.flowerencee.qrpaymentapp.data.model.entity.Transaction
import id.flowerencee.qrpaymentapp.data.model.entity.UserAccount
import id.flowerencee.qrpaymentapp.domain.usecase.transaction.GetAllTransactionFromAccountIdUseCase
import id.flowerencee.qrpaymentapp.domain.usecase.transaction.GetAllTransactionUseCase
import id.flowerencee.qrpaymentapp.domain.usecase.useraccount.GetAllAccountUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HistoriesViewModel(
    private val getAllTransactionUseCase: GetAllTransactionUseCase,
    private val getAllTransactionFromAccountIdUseCase: GetAllTransactionFromAccountIdUseCase,
    private val getAllAccountUseCase: GetAllAccountUseCase
) : ViewModel() {

    private val _transactionList = MutableStateFlow<ArrayList<Transaction>>(arrayListOf())
    val transactionList: StateFlow<ArrayList<Transaction>> get() = _transactionList

    private val _accountList = MutableStateFlow<ArrayList<UserAccount>>(arrayListOf())
    val accountList: StateFlow<ArrayList<UserAccount>> get() = _accountList

    init {
        getAccounts()
        getTransactions()
    }

    fun getTransactions(id: Int? = null) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = when (id == null) {
                true -> getAllTransactionUseCase.execute()
                else -> getAllTransactionFromAccountIdUseCase.execute(id)
            }
            result.collectLatest {
                withContext(Dispatchers.Main) {
                    _transactionList.value = ArrayList(it)
                }
            }
        }
    }

    private fun getAccounts() {
        viewModelScope.launch(Dispatchers.IO) {
            getAllAccountUseCase.execute().collectLatest {
                withContext(Dispatchers.Main) {
                    _accountList.value = ArrayList(it)
                }
            }
        }
    }
}