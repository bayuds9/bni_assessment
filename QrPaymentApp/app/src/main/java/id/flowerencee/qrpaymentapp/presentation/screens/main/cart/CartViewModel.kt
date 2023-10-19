package id.flowerencee.qrpaymentapp.presentation.screens.main.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import id.flowerencee.qrpaymentapp.domain.usecase.portfolio.GetPortfolioUseCase

class CartViewModel(
    private val getPortfolioUseCase: GetPortfolioUseCase
) : ViewModel() {

    fun getPortfolio() = liveData {
        getPortfolioUseCase.execute().collect() {
            emit(it)
        }
    }
}