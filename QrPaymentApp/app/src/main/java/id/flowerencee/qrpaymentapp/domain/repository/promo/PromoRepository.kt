package id.flowerencee.qrpaymentapp.domain.repository.promo

import id.flowerencee.qrpaymentapp.data.model.response.failed.StatusResponse
import id.flowerencee.qrpaymentapp.data.model.response.promo.PromoItem
import kotlinx.coroutines.flow.Flow

interface PromoRepository {
    suspend fun getAllPromo(): Flow<List<PromoItem>>
    suspend fun getStatusResponse(): Flow<StatusResponse>
}