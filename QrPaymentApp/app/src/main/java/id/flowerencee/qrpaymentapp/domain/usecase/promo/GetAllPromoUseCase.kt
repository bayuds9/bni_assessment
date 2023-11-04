package id.flowerencee.qrpaymentapp.domain.usecase.promo

import id.flowerencee.qrpaymentapp.data.model.response.failed.StatusResponses
import id.flowerencee.qrpaymentapp.data.model.response.promo.PromoItem
import id.flowerencee.qrpaymentapp.domain.repository.promo.PromoRepository
import kotlinx.coroutines.flow.Flow

class GetAllPromoUseCase(
    private val promoRepository: PromoRepository
) {

    suspend fun execute(): Flow<List<PromoItem>> = promoRepository.getAllPromo()
    suspend fun getStatus(): Flow<StatusResponses> = promoRepository.getStatusResponse()
}