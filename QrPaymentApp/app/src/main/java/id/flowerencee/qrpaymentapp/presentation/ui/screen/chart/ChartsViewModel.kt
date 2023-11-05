package id.flowerencee.qrpaymentapp.presentation.ui.screen.chart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.flowerencee.qrpaymentapp.data.model.response.portfolio.DoughnutData
import id.flowerencee.qrpaymentapp.data.model.response.portfolio.PortfolioItem
import id.flowerencee.qrpaymentapp.domain.usecase.portfolio.GetPortfolioUseCase
import id.flowerencee.qrpaymentapp.presentation.shared.extension.reformatCurrency
import id.flowerencee.qrpaymentapp.presentation.shared.`object`.TextLabel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChartsViewModel(
    private val getPortfolioUseCase: GetPortfolioUseCase
) : ViewModel() {
    private val _chartDetail = MutableStateFlow<ArrayList<TextLabel>>(arrayListOf())
    val chartDetail: StateFlow<ArrayList<TextLabel>> get() = _chartDetail

    private val _portfolioList = MutableStateFlow<ArrayList<PortfolioItem>>(arrayListOf())
    val portfolioList: StateFlow<ArrayList<PortfolioItem>> get() = _portfolioList

    fun getPortfolio() {
        viewModelScope.launch(Dispatchers.IO) {
            getPortfolioUseCase.execute().collectLatest {
                withContext(Dispatchers.Main) {
                    _portfolioList.value = it
                }
            }
        }
    }

    fun generateChartDetail(chart: DoughnutData?) {
        viewModelScope.launch(Dispatchers.IO) {
            when (chart != null) {
                true -> {
                    val result = ArrayList<TextLabel>()
                    chart.data?.onEachIndexed { index, doughnut ->
                        val data = TextLabel(
                            index,
                            doughnut.trxDate.toString(),
                            doughnut.nominal?.toDouble()?.reformatCurrency("Rp").toString()
                        )
                        result.add(data)
                    }
                    withContext(Dispatchers.Main) {
                        _chartDetail.value = result
                    }
                }

                false -> withContext(Dispatchers.Main) {
                    _chartDetail.value = arrayListOf()
                }
            }
        }
    }
}