package id.flowerencee.qrpaymentapp.data.repository.implementation.portfolio

import id.flowerencee.qrpaymentapp.data.model.response.portfolio.PortfolioItem
import id.flowerencee.qrpaymentapp.data.repository.source.local.portfolio.PortfolioDataSourceImpl
import id.flowerencee.qrpaymentapp.domain.repository.portfolio.PortfolioRepository
import kotlinx.coroutines.flow.Flow

class PortfolioRepositoryImpl(
    private val portfolioDataSourceImpl: PortfolioDataSourceImpl
) : PortfolioRepository {
    override suspend fun getPortfolio(): Flow<ArrayList<PortfolioItem>> {
        return portfolioDataSourceImpl.getCartData()
    }
}