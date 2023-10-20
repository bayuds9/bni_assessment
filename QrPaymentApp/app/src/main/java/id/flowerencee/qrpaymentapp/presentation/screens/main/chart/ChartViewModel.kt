package id.flowerencee.qrpaymentapp.presentation.screens.main.chart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import id.flowerencee.qrpaymentapp.data.model.response.portfolio.DoughnutData
import id.flowerencee.qrpaymentapp.domain.usecase.portfolio.GetPortfolioUseCase
import id.flowerencee.qrpaymentapp.presentation.shared.extension.reformatCurrency
import id.flowerencee.qrpaymentapp.presentation.shared.`object`.TextLabel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChartViewModel(
    private val getPortfolioUseCase: GetPortfolioUseCase
) : ViewModel() {

    private var _chartDetail = MutableLiveData<ArrayList<TextLabel>>()
    val chartDetail: LiveData<ArrayList<TextLabel>> get() = _chartDetail

    fun getPortfolio() = liveData {
        getPortfolioUseCase.execute().collect() {
            emit(it)
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
                            doughnut.nominal?.toDouble()?.reformatCurrency().toString()
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