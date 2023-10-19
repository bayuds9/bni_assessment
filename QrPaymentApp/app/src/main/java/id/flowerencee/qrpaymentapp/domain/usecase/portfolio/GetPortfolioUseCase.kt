package id.flowerencee.qrpaymentapp.domain.usecase.portfolio

import id.flowerencee.qrpaymentapp.data.model.response.portfolio.PortfolioItem
import id.flowerencee.qrpaymentapp.domain.repository.portfolio.PortfolioRepository
import kotlinx.coroutines.flow.Flow

class GetPortfolioUseCase(
    private val portfolioRepository: PortfolioRepository
) {
    suspend fun execute(): Flow<ArrayList<PortfolioItem>> = portfolioRepository.getPortfolio()
}