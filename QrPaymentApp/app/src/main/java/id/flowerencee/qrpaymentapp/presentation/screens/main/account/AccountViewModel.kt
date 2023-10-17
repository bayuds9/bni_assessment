package id.flowerencee.qrpaymentapp.presentation.screens.main.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import id.flowerencee.qrpaymentapp.data.entity.UserAccount
import id.flowerencee.qrpaymentapp.domain.usecase.useraccount.AddUserAccountUseCase
import id.flowerencee.qrpaymentapp.domain.usecase.useraccount.GetAllAccountUseCase
import id.flowerencee.qrpaymentapp.presentation.shared.support.DeLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AccountViewModel(
    private val getAllAccountUseCase: GetAllAccountUseCase,
    private val addUserAccountUseCase: AddUserAccountUseCase
) : ViewModel() {
    private var _success = MutableLiveData<Boolean>()
    val success: LiveData<Boolean> get() = _success
    fun getAllAccount() = liveData {
        getAllAccountUseCase.execute().collect() {
            emit(it)
        }
    }

    fun createAccount(account: UserAccount) = viewModelScope.launch(Dispatchers.IO) {
        val rowId = addUserAccountUseCase.execute(account)
        DeLog.d("haha", "row id $rowId")
        withContext(Dispatchers.Main) {
            _success.value = rowId > 0
        }
    }
}