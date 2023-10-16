package id.flowerencee.qrpaymentapp.presentation.screens.main.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import id.flowerencee.qrpaymentapp.data.entity.UserAccount
import id.flowerencee.qrpaymentapp.domain.usecase.transaction.GetAllTransactionUseCase
import id.flowerencee.qrpaymentapp.domain.usecase.useraccount.AddUserAccountUseCase
import id.flowerencee.qrpaymentapp.domain.usecase.useraccount.GetAllAccountUseCase
import id.flowerencee.qrpaymentapp.presentation.shared.support.DeLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DashboardViewModel(
    private val getAllAccountUseCase: GetAllAccountUseCase,
    private val addUserAccountUseCase: AddUserAccountUseCase,
    private val getAllTransactionUseCase: GetAllTransactionUseCase
) : ViewModel() {

    private var _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading
    fun getUserAccount() = liveData {
        getAllAccountUseCase.execute().collect(){
            emit(it)
        }
    }

    fun addAccount(userAccount: UserAccount) {
        _loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val rowId = addUserAccountUseCase.execute(userAccount)
            withContext(Dispatchers.Main){
                DeLog.d("haha", "created account $rowId")
                _loading.value = false
            }
        }
    }

    fun getLastTransaction() = liveData{
        getAllTransactionUseCase.execute().collect(){
            emit(it)
        }
    }
}