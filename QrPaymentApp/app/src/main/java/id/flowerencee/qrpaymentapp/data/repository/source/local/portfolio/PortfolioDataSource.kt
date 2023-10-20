package id.flowerencee.qrpaymentapp.data.repository.source.local.portfolio

import id.flowerencee.qrpaymentapp.data.model.response.portfolio.PortfolioItem
import kotlinx.coroutines.flow.Flow

interface PortfolioDataSource {
    suspend fun getChartData(): Flow<ArrayList<PortfolioItem>>

}