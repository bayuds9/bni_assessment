package id.flowerencee.qrpaymentapp.domain.usecase.promo

import id.flowerencee.qrpaymentapp.data.model.response.failed.StatusResponse
import id.flowerencee.qrpaymentapp.data.model.response.promo.PromoItem
import id.flowerencee.qrpaymentapp.domain.repository.promo.PromoRepository
import kotlinx.coroutines.flow.Flow

class GetAllPromoUseCase(
    private val promoRepository: PromoRepository
) {
    suspend fun getStatus(): Flow<StatusResponse> = promoRepository.getStatusResponse()
    suspend fun execute(): Flow<List<PromoItem>> = promoRepository.getAllPromo()
}