package id.flowerencee.qrpaymentapp.domain.usecase.promo

import androidx.lifecycle.LiveData
import id.flowerencee.qrpaymentapp.data.model.response.failed.StatusResponse
import id.flowerencee.qrpaymentapp.data.model.response.promo.PromoListResponse
import id.flowerencee.qrpaymentapp.data.model.response.promo.PromoListResponseItem
import id.flowerencee.qrpaymentapp.domain.repository.promo.PromoRepository
import kotlinx.coroutines.flow.Flow

class GetAllPromoUseCase(
    private val promoRepository: PromoRepository
) {
    suspend fun getStatus() : LiveData<StatusResponse> = promoRepository.getStatusResponse()
    suspend fun execute(): Flow<List<PromoListResponseItem>> = promoRepository.getAllPromo()
}