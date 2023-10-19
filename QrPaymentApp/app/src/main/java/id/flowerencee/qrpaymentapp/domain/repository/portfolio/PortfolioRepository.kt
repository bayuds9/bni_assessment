package id.flowerencee.qrpaymentapp.domain.repository.portfolio

import id.flowerencee.qrpaymentapp.data.model.response.portfolio.PortfolioItem
import kotlinx.coroutines.flow.Flow

interface PortfolioRepository {
    suspend fun getPortfolio(): Flow<ArrayList<PortfolioItem>>
}