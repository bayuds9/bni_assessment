package id.flowerencee.qrpayapp.presentation.ui.screens.main.dashboard

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.material.icons.filled.QrCode2
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import id.flowerencee.qrpayapp.data.entity.UserAccount
import id.flowerencee.qrpayapp.domain.usecase.useraccount.AddUserAccountUseCase
import id.flowerencee.qrpayapp.domain.usecase.useraccount.GetAllAccountUseCase
import id.flowerencee.qrpayapp.presentation.support.navigation.Screen
import id.flowerencee.qrpayapp.presentation.objects.BottomNavItem
import id.flowerencee.qrpayapp.support.DeLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val getAllAccountUseCase: GetAllAccountUseCase,
    private val addUserAccountUseCase: AddUserAccountUseCase
) : ViewModel() {
    fun getAllAccount() = liveData {
        getAllAccountUseCase.execute().collectLatest {
            emit(it)
        }
    }

    fun addAccount(account: UserAccount) = viewModelScope.launch(Dispatchers.IO){
        val rowId = addUserAccountUseCase.execute(account)
        DeLog.d("haha", "row id $rowId")
    }
}