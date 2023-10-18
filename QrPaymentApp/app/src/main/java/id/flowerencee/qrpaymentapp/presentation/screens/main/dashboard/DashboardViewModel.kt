package id.flowerencee.qrpaymentapp.presentation.screens.main.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import id.flowerencee.qrpaymentapp.domain.usecase.promo.GetAllPromoUseCase
import id.flowerencee.qrpaymentapp.domain.usecase.transaction.GetLimitedTransactionDescendingUseCase

class DashboardViewModel(
    private val getAllPromoUseCase: GetAllPromoUseCase,
    private val getLimitedTransactionDescendingUseCase: GetLimitedTransactionDescendingUseCase
) : ViewModel() {

    fun getLastTransaction(limit: Int) = liveData {
        getLimitedTransactionDescendingUseCase.execute(limit).collect() {
            emit(it)
        }
    }

    fun getAllPromo() = liveData {
        getAllPromoUseCase.execute().collect() {
            emit(it)
        }
    }

    fun getStatus() = liveData {
        getAllPromoUseCase.getStatus().collect() {
            emit(it)
        }
    }
}