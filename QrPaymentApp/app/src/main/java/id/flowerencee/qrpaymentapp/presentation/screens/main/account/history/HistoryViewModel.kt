package id.flowerencee.qrpaymentapp.presentation.screens.main.account.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import id.flowerencee.qrpaymentapp.domain.usecase.transaction.GetAllTransactionFromAccountIdUseCase
import id.flowerencee.qrpaymentapp.domain.usecase.transaction.GetAllTransactionUseCase
import kotlinx.coroutines.flow.collect

class HistoryViewModel(
    private val getAllTransactionUseCase: GetAllTransactionUseCase,
    private val getAllTransactionFromAccountIdUseCase: GetAllTransactionFromAccountIdUseCase
) : ViewModel() {

    fun getTransactions(id: Int? = null) = liveData {
        when(id == null){
            true -> {
                getAllTransactionUseCase.execute().collect(){
                    emit(it)
                }
            }
            else -> {
                getAllTransactionFromAccountIdUseCase.execute(id).collect(){
                    emit(it)
                }
            }
        }
    }
}